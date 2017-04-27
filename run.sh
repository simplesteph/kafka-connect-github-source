#!/usr/bin/env bash
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
connect-standalone config/worker.properties config/GitHubSourceConnectorExample.properties