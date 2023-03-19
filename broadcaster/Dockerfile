FROM openjdk:16-alpine3.13
LABEL maintainer="Karthick Balathandayuthapani captainbkarthick@gmail.com"
COPY target/broadcaster-0.0.1.jar broadcaster-service.jar
ENTRYPOINT ["java","-jar","/broadcaster-service.jar"]