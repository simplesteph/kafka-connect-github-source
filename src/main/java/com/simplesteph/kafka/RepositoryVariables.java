package com.simplesteph.kafka;
import java.time.Instant;

//This is a class which has all necessary details about a repository
public class RepositoryVariables {
    private String repository;
    private String owner;
    private String repo_name;
    private String topic;
    protected Instant nextQuerySince;
    protected Integer lastIssueNumber;
    protected Integer nextPageToVisit = 1;
    protected Instant lastUpdatedAt;
    public RepositoryVariables(String repository){
        this.repository=repository;
        String components[]=repository.split("[/:]");         //splitting repository string which is of pattern "owner/repo:topic"
        this.owner=components[0];
        this.repo_name=components[1];
        this.topic=components[2];
    }

    public String getOwner(){
        return owner;
    }
    public String getRepoName(){
        return repo_name;
    }

    public String getTopic(){
        return topic;
    }
}
