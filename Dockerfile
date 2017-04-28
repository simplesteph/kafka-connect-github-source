FROM confluentinc/cp-kafka-connect:3.2.0

WORKDIR /kafka-connect-source-github
COPY config config
COPY target target

CMD connect-standalone config/worker.properties config/GitHubSourceConnectorExample.properties