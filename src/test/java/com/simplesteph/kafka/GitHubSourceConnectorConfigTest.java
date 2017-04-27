package com.simplesteph.kafka;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static com.simplesteph.kafka.GitHubSourceConnectorConfig.*;

public class GitHubSourceConnectorConfigTest {

    private ConfigDef configDef = GitHubSourceConnectorConfig.conf();

    private Map<String, String> initialConfig() {
        Map<String, String> baseProps = new HashMap<>();
        baseProps.put(OWNER_CONFIG, "foo");
        baseProps.put(REPO_CONFIG, "bar");
        baseProps.put(SINCE_CONFIG, "2017-04-26T01:23:45Z");
        baseProps.put(BATCH_SIZE_CONFIG, "100");
        baseProps.put(TOPIC_CONFIG, "github-issues");
        return baseProps;
    }


    @Test
    public void doc() {
        System.out.println(GitHubSourceConnectorConfig.conf().toRst());
    }

    @Test
    public void initialConfigIsValid() {
        assert (configDef.validate(initialConfig())
                .stream()
                .allMatch(configValue -> configValue.errorMessages().size() == 0));
    }

    @Test
    public void canReadConfigCorrectly() {
        GitHubSourceConnectorConfig config = new GitHubSourceConnectorConfig(initialConfig());
        config.getAuthPassword();

    }


    @Test
    public void validateSince() {
        Map<String, String> config = initialConfig();
        config.put(SINCE_CONFIG, "not-a-date");
        ConfigValue configValue = configDef.validateAll(config).get(SINCE_CONFIG);
        assert (configValue.errorMessages().size() > 0);
    }

    @Test
    public void validateBatchSize() {
        Map<String, String> config = initialConfig();
        config.put(BATCH_SIZE_CONFIG, "-1");
        ConfigValue configValue = configDef.validateAll(config).get(BATCH_SIZE_CONFIG);
        assert (configValue.errorMessages().size() > 0);

        config = initialConfig();
        config.put(BATCH_SIZE_CONFIG, "101");
        configValue = configDef.validateAll(config).get(BATCH_SIZE_CONFIG);
        assert (configValue.errorMessages().size() > 0);

    }

    @Test
    public void validateUsername() {
        Map<String, String> config = initialConfig();
        config.put(AUTH_USERNAME_CONFIG, "username");
        ConfigValue configValue = configDef.validateAll(config).get(AUTH_USERNAME_CONFIG);
        assert (configValue.errorMessages().size() == 0);
    }

    @Test
    public void validatePassword() {
        Map<String, String> config = initialConfig();
        config.put(AUTH_PASSWORD_CONFIG, "password");
        ConfigValue configValue = configDef.validateAll(config).get(AUTH_PASSWORD_CONFIG);
        assert (configValue.errorMessages().size() == 0);
    }

}