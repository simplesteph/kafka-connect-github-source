package com.simplesteph.kafka;

import com.simplesteph.kafka.Validators.BatchSizeValidator;
import com.simplesteph.kafka.Validators.RepositoriesValidator;
import com.simplesteph.kafka.Validators.TimestampValidator;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class GitHubSourceConnectorConfig extends AbstractConfig {

    public static final String REPOSITORY_TOPIC_SEPARATOR = ",";

    public static final String REPOSITORIES_CONFIG = "github.repositories";
    private static final String REPOSITORIES_DOC =
            "Repositories you'd like to follow.\n"
            + "The format is owner1/repo1,owner1/repo2,owner2/repo3";

    public static final String SINCE_CONFIG = "since.timestamp";
    private static final String SINCE_DOC =
            "Only issues updated at or after this time are returned.\n"
                    + "This is a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.\n"
                    + "Defaults to a year from first launch.";

    public static final String BATCH_SIZE_CONFIG = "batch.size";
    private static final String BATCH_SIZE_DOC = "Number of data points to retrieve at a time. Defaults to 100 (max value)";

    public static final String AUTH_USERNAME_CONFIG = "auth.username";
    private static final String AUTH_USERNAME_DOC = "Optional Username to authenticate calls";

    public static final String AUTH_PASSWORD_CONFIG = "auth.password";
    private static final String AUTH_PASSWORD_DOC = "Optional Password to authenticate calls";


    public GitHubSourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
        super(config, parsedConfig);
    }

    public GitHubSourceConnectorConfig(Map<String, String> parsedConfig) {
        this(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
        return new ConfigDef()
                .define(REPOSITORIES_CONFIG, Type.STRING, "", new RepositoriesValidator(), Importance.HIGH, REPOSITORIES_DOC)
                .define(BATCH_SIZE_CONFIG, Type.INT, 100, new BatchSizeValidator(), Importance.LOW, BATCH_SIZE_DOC)
                .define(SINCE_CONFIG, Type.STRING, ZonedDateTime.now().minusYears(1).toInstant().toString(),
                        new TimestampValidator(), Importance.HIGH, SINCE_DOC)
                .define(AUTH_USERNAME_CONFIG, Type.STRING, "", Importance.HIGH, AUTH_USERNAME_DOC)
                .define(AUTH_PASSWORD_CONFIG, Type.PASSWORD, "", Importance.HIGH, AUTH_PASSWORD_DOC);
    }

    public List<GithubSourceTaskConfig> getTasksConfig() {
        String rawString = this.getString(REPOSITORIES_CONFIG);
        String[] rawArray = rawString.split(REPOSITORY_TOPIC_SEPARATOR);
        ArrayList<String> rawRepositories = new ArrayList<>(Arrays.asList(rawArray));

        ArrayList<GithubSourceTaskConfig> repositoriesAndTopics = new ArrayList<>(rawRepositories.size());
        for (String rawRepository : rawRepositories) {
            String[] split = rawRepository.split("[/:]+");
            repositoriesAndTopics.add(new GithubSourceTaskConfig(split[0], split[1], split[2]));
        }

        return repositoriesAndTopics;
    }

    public Integer getBatchSize() {
        return this.getInt(BATCH_SIZE_CONFIG);
    }

    public Instant getSince() {
        return Instant.parse(this.getString(SINCE_CONFIG));
    }

    public String getAuthUsername() {
        return this.getString(AUTH_USERNAME_CONFIG);
    }

    public String getAuthPassword(){
        return this.getPassword(AUTH_PASSWORD_CONFIG).value();
    }
}
