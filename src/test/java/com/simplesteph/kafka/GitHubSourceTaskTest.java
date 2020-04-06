package com.simplesteph.kafka;

import com.simplesteph.kafka.model.Issue;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.simplesteph.kafka.GitHubSourceConnectorConfig.*;
import static org.junit.Assert.*;

public class GitHubSourceTaskTest {

    private GitHubSourceTask gitHubSourceTask = new GitHubSourceTask();
    private Integer batchSize = 10;

    private Map<String, String> initialConfig() {
        Map<String, String> baseProps = new HashMap<>();
        baseProps.put(REPOS_CONFIG,"apache/kafka:github_issues,kubernetes/kubernetes:github-issues.31,foo-foo/bar_bar:github.issues");
        baseProps.put(SINCE_CONFIG, "2017-01-01T00:00:00Z");
        baseProps.put(BATCH_SIZE_CONFIG, batchSize.toString());
        return baseProps;
    }


    @Test
    public void test() throws IOException {
        gitHubSourceTask.config = new GitHubSourceConnectorConfig(initialConfig());
        RepositoryVariables repoVar=new RepositoryVariables(gitHubSourceTask.config.getReposConfig()[0]);
        repoVar.nextQuerySince=Instant.parse("2017-01-01T00:00:00Z");
        gitHubSourceTask.gitHubHttpAPIClient = new GitHubAPIHttpClient(gitHubSourceTask.config);
        String url = gitHubSourceTask.gitHubHttpAPIClient.constructUrl(repoVar);
        System.out.println(url);
        HttpResponse httpResponse = gitHubSourceTask.gitHubHttpAPIClient.getNextIssuesAPI(repoVar);
        if (httpResponse.getStatusLine().getStatusCode() != 403) {
            assertEquals(200, httpResponse.getStatusLine().getStatusCode());
            assertTrue(httpResponse.getFirstHeader("ETag")!=null);
            assertTrue(httpResponse.getFirstHeader("X-RateLimit-Limit")!=null);
            assertTrue(httpResponse.getFirstHeader("X-RateLimit-Remaining")!=null);
            assertTrue(httpResponse.getFirstHeader("X-RateLimit-Reset")!=null);
            JSONArray jsonArray=new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
            assertTrue(batchSize.intValue() >= jsonArray.length());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            Issue issue = Issue.fromJson(jsonObject);
            assertNotNull(issue);
            assertNotNull(issue.getNumber());
            assertEquals(2072, issue.getNumber().intValue());
        }
    }
}