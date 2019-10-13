# Learning

This project is a companion repository to the [Apache Kafka Connect course on Udemy](https://links.datacumulus.com/kafka-connect-coupon). 

https://links.datacumulus.com/kafka-connect-coupon

# Kafka Connect Source GitHub

This connector allows you to get a stream of issues and pull requests from your GitHub repository, using the GitHub Api: https://developer.github.com/v3/issues/#list-issues-for-a-repository

Issues are pulled based on `updated_at` field, meaning any update to an issue or pull request will appear in the stream. 

The connector writes to topic that is great candidate to demonstrate *log compaction*. It's also a fun way to automate your GitHub workflow. 

It's finally aimed to be an educative example to demonstrate how to write a Source Connector a little less trivial than the `FileStreamSourceConnector` provided in Kafka.

# Contributing

This connector is not perfect and can be improved, please feel free to submit any PR you deem useful. 

# Configuration

```
name=GitHubSourceConnectorDemo
tasks.max=1
connector.class=com.simplesteph.kafka.GitHubSourceConnector
topic=github-issues
github.owner=kubernetes
github.repo=kubernetes
since.timestamp=2017-01-01T00:00:00Z
# I heavily recommend you set those two fields:
auth.username=your_username
auth.password=your_password
```

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

TODO
