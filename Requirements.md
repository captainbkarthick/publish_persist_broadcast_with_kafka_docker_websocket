## Requirements

The deliverable is two part:
1. A working docker-compose setup and configuration for running the solution locally.

The task is to implement a data processing service.

* Set up a running environment with the technologies mentioned below.
* A Readme file containing information you deem useful for someone getting to know 
your code and want to try the system out.
* Develop the application in Java 11 or newer, and Spring Boot as a foundation.
* A REST API
	* An endpoint is taking a JSON payload, and publishes it to a PubSub topic on KAFKA.
  The endpoint must reject invalid payloads.
	Payload structure:
    ```json
    {
      "content": "abrakadabra",
      "timestamp": "2018-10-09 00:12:12+0100"
    }
    ```
	* A PubSub topic consumer is implemented, that persists the freshly received JSON payload to the database.
	* The received JSON payload must also be broadcasted to listening browser clients via Websockets.
	* An endpoint to retrieve all messages persisted in the database; the entities
	must be enriched with the `longest_palindrome_size` property, that contains
	the length of the longest palindrome contained within value ofthe `content` 
	property.
	```json
    [
     {   
      "content": "abrakadabra",
      "timestamp": "2018-10-08 23:12:12+0000",
      "longest_palindrome_size": 3
     }
    ]
    ```
    When computing palindrome length, only alphabetic characters are considered.
    https://en.wikipedia.org/wiki/Palindrome
    
* A simple HTML page is implemented to show the real time message delivery
