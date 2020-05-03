package com.simplesteph.kafka;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.simplesteph.kafka.model.Issue;
import org.json.JSONObject;
import org.junit.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.simplesteph.kafka.GitHubAPIHttpClient.*;
import static com.simplesteph.kafka.GitHubSourceConnectorConfig.*;
import static org.junit.Assert.*;

public class GitHubSourceTaskTest {

    private GitHubSourceTask gitHubSourceTask = new GitHubSourceTask();
    private Integer batchSize = 10;

    private Map<String, String> initialConfig() {
        Map<String, String> baseProps = new HashMap<>();
        baseProps.put(OWNER_CONFIG, "apache");
        baseProps.put(REPO_CONFIG, "kafka");
        baseProps.put(SINCE_CONFIG, "2017-04-26T01:23:44Z");
        baseProps.put(BATCH_SIZE_CONFIG, batchSize.toString());
        baseProps.put(TOPIC_CONFIG, "github-issues");
        return baseProps;
    }


    @Test
    public void test() throws UnirestException {
        gitHubSourceTask.config = new GitHubSourceConnectorConfig(initialConfig());
        gitHubSourceTask.nextPageToVisit = 1;
        gitHubSourceTask.nextQuerySince = Instant.parse("2017-01-01T00:00:00Z");
        gitHubSourceTask.gitHubHttpAPIClient = new GitHubAPIHttpClient(gitHubSourceTask.config);
        String url = gitHubSourceTask.gitHubHttpAPIClient.constructUrl(gitHubSourceTask.nextPageToVisit, gitHubSourceTask.nextQuerySince);
        System.out.println(url);
        HttpResponse<JsonNode> httpResponse = gitHubSourceTask.gitHubHttpAPIClient.getNextIssuesAPI(gitHubSourceTask.nextPageToVisit, gitHubSourceTask.nextQuerySince);
        if (httpResponse.getStatus() != 403) {
            assertEquals(200, httpResponse.getStatus());
            Set<String> headers = httpResponse.getHeaders().keySet();
            assertTrue(headers.contains(X_RATELIMIT_LIMIT_HEADER));
            assertTrue(headers.contains(X_RATELIMIT_REMAINING_HEADER));
            assertTrue(headers.contains(X_RATELIMIT_RESET_HEADER));
            assertEquals(batchSize.intValue(), httpResponse.getBody().getArray().length());
            JSONObject jsonObject = (JSONObject) httpResponse.getBody().getArray().get(0);
            Issue issue = Issue.fromJson(jsonObject);
            assertNotNull(issue);
            assertNotNull(issue.getNumber());
            assertEquals(2072, issue.getNumber().intValue());
        }
    }
}
