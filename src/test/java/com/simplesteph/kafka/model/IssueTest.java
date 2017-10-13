package com.simplesteph.kafka.model;

import com.simplesteph.kafka.GitHubSchemas;
import com.simplesteph.kafka.GitHubSourceTask;
import org.apache.kafka.connect.data.Struct;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueTest {
    String issueStr = "{\n" +
            "  \"url\": \"https://api.github.com/repos/apache/kafka/issues/2800\",\n" +
            "  \"repository_url\": \"https://api.github.com/repos/apache/kafka\",\n" +
            "  \"labels_url\": \"https://api.github.com/repos/apache/kafka/issues/2800/labels{/name}\",\n" +
            "  \"comments_url\": \"https://api.github.com/repos/apache/kafka/issues/2800/comments\",\n" +
            "  \"events_url\": \"https://api.github.com/repos/apache/kafka/issues/2800/events\",\n" +
            "  \"html_url\": \"https://github.com/apache/kafka/pull/2800\",\n" +
            "  \"id\": 219155037,\n" +
            "  \"number\": 2800,\n" +
            "  \"title\": \"added interface to allow producers to create a ProducerRecord without…\",\n" +
            "  \"user\": {\n" +
            "    \"login\": \"simplesteph\",\n" +
            "    \"id\": 20851561,\n" +
            "    \"avatar_url\": \"https://avatars3.githubusercontent.com/u/20851561?v=3\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/simplesteph\",\n" +
            "    \"html_url\": \"https://github.com/simplesteph\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/simplesteph/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/simplesteph/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/simplesteph/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/simplesteph/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/simplesteph/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/simplesteph/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/simplesteph/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/simplesteph/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/simplesteph/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  \"labels\": [],\n" +
            "  \"state\": \"closed\",\n" +
            "  \"locked\": false,\n" +
            "  \"assignee\": null,\n" +
            "  \"assignees\": [],\n" +
            "  \"milestone\": null,\n" +
            "  \"comments\": 12,\n" +
            "  \"created_at\": \"2017-04-04T06:47:09Z\",\n" +
            "  \"updated_at\": \"2017-04-19T22:36:21Z\",\n" +
            "  \"closed_at\": \"2017-04-19T22:36:21Z\",\n" +
            "  \"pull_request\": {\n" +
            "    \"url\": \"https://api.github.com/repos/apache/kafka/pulls/2800\",\n" +
            "    \"html_url\": \"https://github.com/apache/kafka/pull/2800\",\n" +
            "    \"diff_url\": \"https://github.com/apache/kafka/pull/2800.diff\",\n" +
            "    \"patch_url\": \"https://github.com/apache/kafka/pull/2800.patch\"\n" +
            "  },\n" +
            "  \"body\": \"… specifying a partition, making it more obvious that the parameter partition can be null\"\n" +
            "}";

    private JSONObject issueJson = new JSONObject(issueStr);


    @Test
    public void canParseJson(){
        // issue
        Issue issue = Issue.fromJson(issueJson);
        assertEquals(issue.getUrl(), "https://api.github.com/repos/apache/kafka/issues/2800");
        assertEquals(issue.getTitle(), "added interface to allow producers to create a ProducerRecord without…");
        assertEquals(issue.getCreatedAt().toString(), "2017-04-04T06:47:09Z");
        assertEquals(issue.getUpdatedAt().toString(), "2017-04-19T22:36:21Z");
        assertEquals(issue.getNumber(), (Integer) 2800);
        assertEquals(issue.getState(), "closed");

        // user
        User user = issue.getUser();
        assertEquals(user.getId(), (Integer) 20851561);
        assertEquals(user.getUrl(), "https://api.github.com/users/simplesteph");
        assertEquals(user.getHtmlUrl(), "https://github.com/simplesteph");
        assertEquals(user.getLogin(), "simplesteph");

        // pr
        PullRequest pullRequest = issue.getPullRequest();
        assertNotNull(pullRequest);
        assertEquals(pullRequest.getUrl(), "https://api.github.com/repos/apache/kafka/pulls/2800");
        assertEquals(pullRequest.getHtmlUrl(), "https://github.com/apache/kafka/pull/2800");

    }

    @Test
    public void convertsToStruct(){
        // issue
        Issue issue = Issue.fromJson(issueJson);
        Struct struct = new GitHubSourceTask().buildRecordValue(issue);
        assertEquals(struct.get(GitHubSchemas.CREATED_AT_FIELD).getClass(), Date.class);
    }

}
