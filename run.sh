#!/usr/bin/env bash
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
if hash docker 2>/dev/null; then
    # for docker lovers
    docker build . -t simplesteph/kafka-connect-source-github:1.0
    docker run --net=host --rm -t \
           -v $(pwd)/offsets:/kafka-connect-source-github/offsets \
           simplesteph/kafka-connect-source-github:1.0
elif hash connect-standalone 2>/dev/null; then
    # for mac users who used homebrew
    connect-standalone config/worker.properties config/GitHubSourceConnectorExample.properties
elif [[ -z $KAFKA_HOME ]]; then
    # for people who installed kafka vanilla
    $KAFKA_HOME/bin/connect-standalone.sh $KAFKA_HOME/etc/schema-registry/connect-avro-standalone.properties config/GitHubSourceConnectorExample.properties
elif [[ -z $CONFLUENT_HOME ]]; then
    # for people who installed kafka confluent flavour
    $CONFLUENT_HOME/bin/connect-standalone $CONFLUENT_HOME/etc/schema-registry/connect-avro-standalone.properties config/GitHubSourceConnectorExample.properties
else
    printf "Couldn't find a suitable way to run kafka connect for you.\n \
Please install Docker, or download the kafka binaries and set the variable KAFKA_HOME."
fi;
