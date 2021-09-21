FROM openjdk:11
LABEL mantainer="carlos.pinho@ucsal.edu.br"

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/spring_boot

COPY /target/spring-boot*.jar medical-register-api.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar medical-register-api.jar --spring.profiles.active=${PROFILE}