
# syntax=docker/dockerfile:1

FROM openjdk:17-alpine3.14
RUN addgroup -S coileach && adduser -S coileach -G coileach
USER coileach:coileach
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} fpl-api-1.jar
ENTRYPOINT ["java","-jar","/fpl-api-1.jar"]
EXPOSE 8080