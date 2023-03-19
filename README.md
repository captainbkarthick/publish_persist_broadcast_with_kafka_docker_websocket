# DATA PROCESSING APPLICATION - Overview  
This application consists of 3 Springboot services named as **Publisher**, **Persister** and **Broadcaster**. These services uses **Kafka** to stream and read data.  
The "Publisher" service streams data (*Producer*).  
The "Persister" and "Broadcaster" services reads the data streamed by Producer service (*Consumers*).  

All these services are containerized and configured to run in **Docker-Compose**.  
These services can run locally as individual services if local Kafka Setup is available.


## NFR
* Java -16
* Springboot - 2.4.5
* kafka
* Lombok
* Docker
* H2 Database
* Swagger Documentation


## SERVICES
### => Publisher Service
* This service exposes an endpoint, which gets data to publish, as a JSON message through http request.  
* The received JSON message is then published to a *Kafka* topic. 
* **Endpoint(s)**  
  Below mentioned URLs have DOCKER port numbers. Change the port number while running in Local.  
  LocalPort: `8060`  
  DockerPort:`18600`
  * GET - http://localhost:18600/publisher/test - To test if the service is running.
  * POST - http://localhost:18600/publisher/publish - To publish the data. The request body should be of below format.
  ```json
    {
      "content": "abrakadabra",
      "timestamp": "2018-10-09 00:12:12+0100"
    }
    ```  
  * Detailed information about the Path/Query parameter(s), default values and its examples can be found from Swagger UI: http://localhost:18600/swagger-ui.html  
        
------------------------  
### => Persister Service

* This service listenes to a Kafka topic and consumes the messages streamed by the Publisher service.
* The received messages are persisted in the H2 Database.
* While persisting the data, `messageId` is created for each message and also the `rowCreatedTime` and `lastUpdatedTime` are stored.
* This services exposes endpoints to fetch and return the record(s) from database.
* The data returned is enriched with `longest_palindrome_size` for the given message content.
* **Endpoint(s)**  
  Below mentioned URLs have DOCKER port numbers. Change the port number while running in Local.  
  LocalPort: `8070`  
  DockerPort:`18700`
  * GET - http://localhost:18700/persister/test - To test if the service is running.
  * GET - http://localhost:18700/persister/get-all-data - To get all data from the database.  
  * GET - http://localhost:18700/persister/get-temporal-data/{fieldName} - To get Temporal Data, based on the database columns which are of `timestamp` datatype.
  * GET - http://localhost:18700/persister/get-records/{fieldName} - To get records(s) from database which matches the `fieldName` to the `value` provided in the request.

* Detailed information about the Path/Query parameter(s), default values and its examples can be found from Swagger UI: http://localhost:18700/swagger-ui.html  
* H2 Database Console: http://localhost:18700/h2-console  
  * DB Name: `jdbc:h2:mem:falconDB`
  * User Name: `sa`
  * Password: `Password should be empty`
--------------------------  
### => Broadcaster Service  

* This service listenes to the Kafka topic and consumes the messages streamed by the Publisher service.
* Upon receiving the messages, the messages are broadcasted to the client through WebSocket.
* **Endpoint(s)**  
  Below mentioned URLs have DOCKER port numbers. Change the port number while running in Local.  
  LocalPort: `8090`  
  DockerPort:`18900`  
  * GET - http://localhost:18900/broadcaster/test - To test if the service is running.
  * GET - http://localhost:18900/broadcaster/testBroadcast - This endpoint will broadcast a test message to the Listeners.
  * http://localhost:18900 - To listen to the broadcasted message.  
 ------------------------  
 
 
## LOCAL SETUP
* Setup Java.
* Setup Maven (This is Optional. We can use Maven Wrapper in the project to build the services)
* Setup Eclipse
* Download Lombok.jar from the URL https://projectlombok.org/download and execute the jar with command `java -jar lombok.jar` 
* Clone this Project
* Install Kafka - Refer https://kafka.apache.org/quickstart
  * Reference to fix Zookeper Startup issues in Windows - [Check here.](https://stackoverflow.com/questions/25037263/apache-kafka-error-on-windows-couldnot-find-or-load-main-class-quorumpeermain/29956869#29956869)
  * Reference to fix Kafka Startup issues in Windows - [Check here.](https://github.com/kafka-dev/kafka/issues/61)
* Install Docker - Refer https://docs.docker.com/compose/install/
  * Reference to fix Linux Kernel issues - [Check here.](https://docs.microsoft.com/en-us/windows/wsl/install-win10#step-4---download-the-linux-kernel-update-package)
  
  
## Local Run Commands
* `KAFKA_ROOT_PATH = Installation directory OF Kafka` Example: `C:\PILOT\Falcon\Softwares\kafka_2.13-2.8.0`
* **Commands** - Open new command window and execute the command `cd %KAFKA_ROOT_PATH%\bin\windows` before executing below commands.  
  * Start Zookeper: `zookeeper-server-start.bat ..\..\config\zookeeper.properties`
  * Start Kafka : `kafka-server-start.bat ..\..\config\server.properties`
  * Create Topic: `kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic content-timestamp-topic`
  * Kafka Console Producer: `kafka-console-producer.bat --broker-list localhost:9092 --topic content-timestamp-topic`
  * kafka Console Consumer: `kafka-console-consumer.bat --topic content-timestamp-topic --from-beginning --bootstrap-server localhost:9092`

  
  
## Docker Run Commands
#### => Build Docker Images
* Before building docker images, make sure to execute `mvn clean install`(*using installed maven*) or `mvnw clean install`(*from maven wrapper in the service code*) for all the individual services.
* **Publisher Service** -  Run below commands in command prompt
  * `cd %REPOSITORY_ROOT_PATH%/publisher`
  * `docker build --tag=publisher-service:latest`

* **Persister Service** -  Run below commands in command prompt
  * `cd %REPOSITORY_ROOT_PATH%/persister`
  * `docker build --tag=persister-service:latest`
 
* **Broadcaster Service** -  Run below commands in command prompt
  * `cd %REPOSITORY_ROOT_PATH%/broadcaster`
  * `docker build --tag=broadcaster-service:latest`
  
#### => Run in Docker-Compose
* Execute below commands
  * `cd %REPOSITORY_ROOT_PATH%`
  * `docker-compose up --build`


  
## REFERENCES
* [Kafka-Local](https://www.confluent.io/blog/apache-kafka-spring-boot-application)
* [Kafka-Docker](https://linuxhint.com/docker_compose_kafka)
* [Spring Data JPA](https://stackabuse.com/guide-to-spring-data-jpa)
* [Docker-Compose](https://www.baeldung.com/dockerizing-spring-boot-application)
* [Running Kafka in Docker](https://medium.com/@marcelo.hossomi/running-kafka-in-docker-machine-64d1501d6f0b)
* [Web Socket](https://spring.io/guides/gs/messaging-stomp-websocket)