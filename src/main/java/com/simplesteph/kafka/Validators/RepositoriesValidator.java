package com.simplesteph.kafka.Validators;

import com.simplesteph.kafka.GitHubSourceConnectorConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RepositoriesValidator implements ConfigDef.Validator {

    private static Logger log = LoggerFactory.getLogger(RepositoriesValidator.class);

    @Override
    public void ensureValid(String name, Object value) {
        String rawString = (String) value;

        if (rawString != null && !rawString.isEmpty()) {
            String[] rawArray = rawString.split(GitHubSourceConnectorConfig.REPOSITORY_TOPIC_SEPARATOR);

            ArrayList<String> rawRepositories = new ArrayList<>(Arrays.asList(rawArray));
            for (String rawRepository : rawRepositories) {
                List<String> split = new ArrayList<>(Arrays.asList(rawRepository.split("[/:]+")));
                if (split.size() != 3 || split.stream().anyMatch(x -> x.isEmpty()))
                    throw new ConfigException(name, value, "Invalid repository configuration '" + rawRepository + "'");
            }
        }
    }
}
