FROM openjdk:16-alpine3.13
LABEL maintainer="Karthick Balathandayuthapani captainbkarthick@gmail.com"
COPY target/publisher-0.0.1.jar publisher-service.jar
ENTRYPOINT ["java","-jar","/publisher-service.jar"]