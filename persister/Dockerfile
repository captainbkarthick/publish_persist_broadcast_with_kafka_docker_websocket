FROM openjdk:16-alpine3.13
LABEL maintainer="Karthick Balathandayuthapani captainbkarthick@gmail.com"
COPY target/persister-0.0.1.jar persister-service.jar
ENTRYPOINT ["java","-jar","/persister-service.jar"]