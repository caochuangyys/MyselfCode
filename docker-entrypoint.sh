#!/bin/sh
set -e

JAVA_OPTS="-server -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="$JAVA_OPTS -Xms${JVM_XMS}"
JAVA_OPTS="$JAVA_OPTS -Xmx${JVM_XMX}"

java ${JAVA_OPTS} -jar /srv/app.jar