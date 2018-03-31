package com.simplesteph.kafka;

import java.util.Map;

public class GithubSourceTaskConfig {

    public static final String TASKCONFIG_OWNER_PARAMETER ="TASKCONFIG_OWNER_PARAMETER";
    public static final String TASKCONFIG_NAME_PARAMETER ="TASKCONFIG_NAME_PARAMETER";
    public static final String TASKCONFIG_TOPIC_PARAMETER ="TASKCONFIG_TOPIC_PARAMETER";

    private String owner;
    private String repository;
    private String topic;

    @Override
    public String toString() {
        return "GithubSourceTaskConfig{" +
                "owner='" + owner + '\'' +
                ", repository='" + repository + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }

    public String getOwner() {
        return owner;
    }

    public String getRepository() {
        return repository;
    }

    public String getTopic() {
        return topic;
    }

    public GithubSourceTaskConfig(Map<String, String> map) {
        owner = map.get(TASKCONFIG_OWNER_PARAMETER);
        repository = map.get(TASKCONFIG_NAME_PARAMETER);
        topic = map.get(TASKCONFIG_TOPIC_PARAMETER);
    }

    public GithubSourceTaskConfig(String owner, String repository, String topic) {

        this.owner = owner;
        this.repository = repository;
        this.topic = topic;
    }
}