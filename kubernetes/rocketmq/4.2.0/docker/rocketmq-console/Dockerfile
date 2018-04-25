FROM openjdk:8-jdk-alpine

VOLUME /tmp
ARG JAR_FILE=rocketmq-console-ng-1.0.0.jar
ADD target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]