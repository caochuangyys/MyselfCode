FROM openjdk:8-jdk-alpine

ENV TZ='Asia/Shanghai'

ARG run_env=prod
ARG jvm_xms=256m
ARG jvm_xmx=1024m

ENV RUN_ENV=$run_env \
    JVM_XMS=$jvm_xms \
    JVM_XMX=$jvm_xmx

ADD docker-entrypoint.sh /
ADD /sg-api/target/sg-app-api.jar /srv/app.jar

ENTRYPOINT ["sh","/docker-entrypoint.sh"]