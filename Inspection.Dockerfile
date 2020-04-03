## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/centos-quarkus-maven:20.0.0-java11 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
USER root
RUN chown -R quarkus /usr/src/app
USER quarkus
RUN mvn -f /usr/src/app/pom.xml package -Dmaven.test.skip=true
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-agentlib:native-image-agent=config-merge-dir=/data/", "-jar",  "/usr/src/app/target/control-quarkus-1.0.0-SNAPSHOT-runner.jar"]
