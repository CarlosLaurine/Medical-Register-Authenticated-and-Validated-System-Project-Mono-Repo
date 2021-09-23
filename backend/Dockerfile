# escape=`
FROM maven:3.8-openjdk-11 as maven1
LABEL mantainer="carlos.pinho@ucsal.edu.br"
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn clean install -Dmaven.test.skip=true
RUN mvn package -Dmaven.test.skip=true && cp target/medical-register-api-*.jar medical-register-api.jar

FROM openjdk:11 as application
LABEL mantainer="carlos.pinho@ucsal.edu.br"
COPY --from=maven1 /root/.m2 /root/.m2
WORKDIR /app

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

COPY --from=maven1 /app/medical-register-api.jar ./medical-register-api.jar
SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

RUN java ${ADDITIONAL_OPTS} -jar /app/medical-register-api.jar --spring.profiles.active=${PROFILE}

FROM maven1 as tests
CMD mvn clean test


