# Kafka Connect Source GitHub

This connector allows you to get a stream of issues and pull requests from GitHub repositories, using the GitHub Api: https://developer.github.com/v3/issues/#list-issues-for-a-repository

Issues are pulled based on `updated_at` field, meaning any update to an issue or pull request will appear in the stream. 

The connector writes to topic that is great candidate to demonstrate *log compaction*. It's also a fun way to automate your GitHub workflow. 

# Configuration

```
name=GitHubSourceConnectorDemo
tasks.max=1
connector.class=com.simplesteph.kafka.GitHubSourceConnector
github.repos=kubernetes/kubernetes:github-issues-kubernetes,apache/kafka:github-issues-kafka
since.timestamp=2017-01-01T00:00:00Z
# I heavily recommend you set those two fields:
auth.username=your_username
auth.password=your_password
```
Note: Configuration for github.repos should be set and should follow the pattern owner1/repo1:topic1,owner2/repo2:topic2 ....

You can control the number of tasks to run by using *tasks.max*. This allows work to be divided from task i.e., each task will be assigned few repositories ans will 
fetch issues for those repositories. 

Set *since.timestamp* to fetch the issues of repositories which have been updated after the required timestamp.

# Running in development

Note: Java 8 is required for this connector. 
Make sure `config/worker.properties` is configured to wherever your kafka cluster is

```
./build.sh
./run.sh 
```

The simplest way to run `run.sh` is to have docker installed. It will pull a Dockerfile and run the connector in standalone mode above it. 

# Deploying

Note: Java 8 is required for this connector. 

#### Distributed Mode

Build the project using `./build.sh`.

Paste the folder `target/kafka-connnect-github-source-1.1-package /share/java/kafka-connect-github-source`(this folder has all jars of the project) 
in connect-workers' `plugin.path`(can be found in the connect-workers' properties) 
directory. The connect-worker should be able to detect `GitHubSourceConnector`.

# Contributing

This connector can be improved much, please feel free to submit any PR you deem useful.