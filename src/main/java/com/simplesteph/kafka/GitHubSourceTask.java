package com.simplesteph.kafka;

import com.simplesteph.kafka.model.Issue;
import com.simplesteph.kafka.model.PullRequest;
import com.simplesteph.kafka.model.User;
import com.simplesteph.kafka.utils.DateUtils;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import static com.simplesteph.kafka.GitHubSchemas.*;


public class GitHubSourceTask extends SourceTask {
    private static final Logger log = LoggerFactory.getLogger(GitHubSourceTask.class);
    public GitHubSourceConnectorConfig config;

    ArrayList<RepositoryVariables> repositoryList;
    GitHubAPIHttpClient gitHubHttpAPIClient;

    int RoundRobinNumber;
    int NumOfReposToFollow;

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        //Do things here that are required to start your task. This could be open a connection to a database, etc.
        config = new GitHubSourceConnectorConfig(map);
        initializeLastVariables();
        gitHubHttpAPIClient = new GitHubAPIHttpClient(config);
    }

    private void initializeLastVariables(){
        //Initializing last variables of all the Repositories given to the task
        String repos[]=config.getReposConfig();
        repositoryList=new ArrayList<RepositoryVariables>(repos.length);
        RoundRobinNumber=0;
        NumOfReposToFollow=repos.length;
        for(String repo:repos) {
            RepositoryVariables RepoVar=new RepositoryVariables(repo);
            Map<String, Object> lastSourceOffset = null;
            lastSourceOffset = context.offsetStorageReader().offset(sourcePartition(RepoVar));
            if (lastSourceOffset == null) {
                // we haven't fetched anything yet, so we initialize to given configuration timestamp
                RepoVar.nextQuerySince = config.getSince();
                RepoVar.nextPageToVisit=1;
                RepoVar.lastIssueNumber = -1;
            } else {
                Object updatedAt = lastSourceOffset.get(UPDATED_AT_FIELD);
                Object issueNumber = lastSourceOffset.get(NUMBER_FIELD);
                Object nextPage = lastSourceOffset.get(NEXT_PAGE_FIELD);
                if (updatedAt != null && (updatedAt instanceof String)) {
                    RepoVar.nextQuerySince = Instant.parse((String) updatedAt);
                }
                if (issueNumber != null && (issueNumber instanceof String)) {
                    RepoVar.lastIssueNumber = Integer.valueOf((String) issueNumber);
                }
                if (nextPage != null && (nextPage instanceof String)) {
                    RepoVar.nextPageToVisit = Integer.valueOf((String) nextPage);
                }
            }
            repositoryList.add(RepoVar);
        }
    }



    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        gitHubHttpAPIClient.sleepIfNeed();

        RepositoryVariables repoVar=repositoryList.get(RoundRobinNumber);

        // fetch data
        final ArrayList<SourceRecord> records = new ArrayList<>();
        JSONArray issues = gitHubHttpAPIClient.getNextIssues(repoVar);
        // we'll count how many results we get with i
        int i = 0;
        for (Object obj : issues) {
            Issue issue = Issue.fromJson((JSONObject) obj);
            SourceRecord sourceRecord = generateSourceRecord(issue,repoVar);
            records.add(sourceRecord);
            i += 1;
            repoVar.lastUpdatedAt = issue.getUpdatedAt();
        }
        if (i > 0) log.info(String.format("Fetched %s record(s)", i));
        if (i == config.getBatchSize()){
            // we have reached a full batch, we need to get the next one
            repoVar.nextPageToVisit += 1;
        }
        else {
            repoVar.nextQuerySince = repoVar.lastUpdatedAt.plusSeconds(1);
            repoVar.nextPageToVisit = 1;
            gitHubHttpAPIClient.sleep();
        }
        RoundRobinNumber=(RoundRobinNumber+1)%NumOfReposToFollow;
        return records;
    }

    private SourceRecord generateSourceRecord(Issue issue,RepositoryVariables repoVar) {
        return new SourceRecord(
                sourcePartition(repoVar),
                sourceOffset(repoVar),
                repoVar.getTopic(),
                null, // partition will be inferred by the framework
                KEY_SCHEMA,
                buildRecordKey(issue,repoVar),
                VALUE_SCHEMA,
                buildRecordValue(issue),
                issue.getUpdatedAt().toEpochMilli());
    }

    @Override
    public void stop() {
        // Do whatever is required to stop your task.
        try {
            log.info("Closing gitHubHttpAPIClient");
            gitHubHttpAPIClient.close();
        } catch (IOException e) {
            log.error("Exception in closing gitHubHttpAPIClient");
            e.printStackTrace();
        }
    }

    private Map<String, String> sourcePartition(RepositoryVariables RepoVar) {
        Map<String, String> map = new HashMap<>();
        map.put(OWNER_FIELD, RepoVar.getOwner());
        map.put(REPOSITORY_FIELD, RepoVar.getRepoName());
        return map;
    }

    private Map<String, String> sourceOffset(RepositoryVariables repoVar) {
        Map<String, String> map = new HashMap<>();
        map.put(UPDATED_AT_FIELD,repoVar.nextQuerySince.toString());
        map.put(NEXT_PAGE_FIELD, repoVar.nextPageToVisit.toString());
        return map;
    }

    private Struct buildRecordKey(Issue issue,RepositoryVariables repoVar){
        // Key Schema
        Struct key = new Struct(KEY_SCHEMA)
                .put(OWNER_FIELD, repoVar.getOwner())
                .put(REPOSITORY_FIELD, repoVar.getRepoName())
                .put(NUMBER_FIELD, issue.getNumber());

        return key;
    }

    public Struct buildRecordValue(Issue issue){

        // Issue top level fields
        Struct valueStruct = new Struct(VALUE_SCHEMA)
                .put(URL_FIELD, issue.getUrl())
                .put(TITLE_FIELD, issue.getTitle())
                .put(CREATED_AT_FIELD, Date.from(issue.getCreatedAt()))
                .put(UPDATED_AT_FIELD, Date.from(issue.getUpdatedAt()))
                .put(NUMBER_FIELD, issue.getNumber())
                .put(STATE_FIELD, issue.getState());

        // User is mandatory
        User user = issue.getUser();
        Struct userStruct = new Struct(USER_SCHEMA)
                .put(USER_URL_FIELD, user.getUrl())
                .put(USER_ID_FIELD, user.getId())
                .put(USER_LOGIN_FIELD, user.getLogin());
        valueStruct.put(USER_FIELD, userStruct);

        // Pull request is optional
        PullRequest pullRequest = issue.getPullRequest();
        if (pullRequest != null) {
            Struct prStruct = new Struct(PR_SCHEMA)
                    .put(PR_URL_FIELD, pullRequest.getUrl())
                    .put(PR_HTML_URL_FIELD, pullRequest.getHtmlUrl());
            valueStruct.put(PR_FIELD, prStruct);
        }

        return valueStruct;
    }

}