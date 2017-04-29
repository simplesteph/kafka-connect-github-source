FROM confluentinc/cp-kafka-connect:3.2.0

WORKDIR /kafka-connect-source-github
COPY config config
COPY target target

VOLUME /kafka-connect-source-github/config
VOLUME /kafka-connect-source-github/offsets

CMD CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')" connect-standalone config/worker.properties config/GitHubSourceConnectorExample.properties