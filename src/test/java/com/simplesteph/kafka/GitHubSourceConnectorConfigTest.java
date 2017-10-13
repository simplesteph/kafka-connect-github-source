package com.simplesteph.kafka;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static com.simplesteph.kafka.GitHubSourceConnectorConfig.*;
import static org.junit.Assert.*;

public class GitHubSourceConnectorConfigTest {

    private ConfigDef configDef = GitHubSourceConnectorConfig.conf();
    private Map<String, String> config;

    @Before
    public void setUpInitialConfig() {
        config = new HashMap<>();
        config.put(OWNER_CONFIG, "foo");
        config.put(REPO_CONFIG, "bar");
        config.put(SINCE_CONFIG, "2017-04-26T01:23:45Z");
        config.put(BATCH_SIZE_CONFIG, "100");
        config.put(TOPIC_CONFIG, "github-issues");
    }

    @Test
    public void doc() {
        System.out.println(GitHubSourceConnectorConfig.conf().toRst());
    }

    @Test
    public void initialConfigIsValid() {
        assertTrue(configDef.validate(config)
                .stream()
                .allMatch(configValue -> configValue.errorMessages().size() == 0));
    }

    @Test
    public void canReadConfigCorrectly() {
        GitHubSourceConnectorConfig config = new GitHubSourceConnectorConfig(this.config);
        config.getAuthPassword();

    }

    @Test
    public void validateSince() {
        config.put(SINCE_CONFIG, "not-a-date");
        ConfigValue configValue = configDef.validateAll(config).get(SINCE_CONFIG);
        assertTrue(configValue.errorMessages().size() > 0);
    }

    @Test
    public void validateBatchSize() {
        config.put(BATCH_SIZE_CONFIG, "-1");
        ConfigValue configValue = configDef.validateAll(config).get(BATCH_SIZE_CONFIG);
        assertTrue(configValue.errorMessages().size() > 0);

        config.put(BATCH_SIZE_CONFIG, "101");
        configValue = configDef.validateAll(config).get(BATCH_SIZE_CONFIG);
        assertTrue(configValue.errorMessages().size() > 0);
    }

    @Test
    public void validateUsername() {
        config.put(AUTH_USERNAME_CONFIG, "username");
        ConfigValue configValue = configDef.validateAll(config).get(AUTH_USERNAME_CONFIG);
        assertEquals(configValue.errorMessages().size(), 0);
    }

    @Test
    public void validatePassword() {
        config.put(AUTH_PASSWORD_CONFIG, "password");
        ConfigValue configValue = configDef.validateAll(config).get(AUTH_PASSWORD_CONFIG);
        assertEquals(configValue.errorMessages().size(), 0);
    }

}