#!/usr/bin/env bash
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
if hash docker 2>/dev/null; then
    # for docker lovers
    docker run -e CLASSPATH=$CLASSPATH \
           --net=host --rm \
           -v $(pwd):/kafka-connect-github-source \
           -w /kafka-connect-github-source \
           confluentinc/cp-kafka-connect:3.2.0 \
           connect-standalone config/worker.properties config/GitHubSourceConnectorExample.properties
elif hash connect-standalone 2>/dev/null; then
    # for mac users who used homebrew
    connect-standalone config/worker.properties config/GitHubSourceConnectorExample.properties
elif [[ -z $KAFKA_HOME ]]; then
    # for people who installed kafka vanilla
    $KAFKA_HOME/bin/connect-standalone.sh $KAFKA_HOME/etc/schema-registry/connect-avro-standalone.properties config/MySourceConnector.properties
elif [[ -z $CONFLUENT_HOME ]]; then
    # for people who installed kafka confluent flavour
    $CONFLUENT_HOME/bin/connect-standalone $CONFLUENT_HOME/etc/schema-registry/connect-avro-standalone.properties config/MySourceConnector.properties
else
    printf "Couldn't find a suitable way to run kafka connect for you.\n \
Please install Docker, or download the kafka binaries and set the variable KAFKA_HOME."
fi;