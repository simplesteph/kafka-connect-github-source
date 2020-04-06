package com.simplesteph.kafka;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.simplesteph.kafka.GitHubSourceConnectorConfig.*;
import static org.junit.Assert.*;

public class GitHubSourceConnectorTest {

  private Map<String, String> initialConfig() {
    Map<String, String> baseProps = new HashMap<>();
    baseProps.put(REPOS_CONFIG,"kubernetes/kubernetes:github-issues.31,foo-foo/bar_bar:github.issues,foo-bar/bar_foo:github.issues");
    baseProps.put(SINCE_CONFIG, "2017-04-26T01:23:45Z");
    baseProps.put(BATCH_SIZE_CONFIG, "100");
    return (baseProps);
  }

  @Test
  public void taskConfigsShouldReturnOneTaskConfig() {
      GitHubSourceConnector gitHubSourceConnector = new GitHubSourceConnector();
      gitHubSourceConnector.start(initialConfig());
      assertEquals(gitHubSourceConnector.taskConfigs(1).size(),1);
      assertEquals(gitHubSourceConnector.taskConfigs(2).size(),2);
      assertEquals(gitHubSourceConnector.taskConfigs(3).size(),3);
      assertEquals(gitHubSourceConnector.taskConfigs(4).size(),3);
      assertEquals(gitHubSourceConnector.taskConfigs(10).size(),3);
  }
}
