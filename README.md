# Money-Transfer-Service
 Simple implementation of ReSTful API for money transfers between accounts.
#ReSTful API for funds transfers between accounts.


## Technology stack
- [Java 8](https://docs.oracle.com/javase/8/docs/)
- [Jersey 2.29.1](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/index.html)
- [grizzly2](https://github.com/jersey/jersey/tree/master/containers/grizzly2-http)
- [Maven](https://maven.apache.org/)
- [JUnit 5.5.2](https://junit.org/junit5/docs/current/user-guide/)

## How to run standalone jar
- java -jar ".\target\money-transfer-service-1.0-SNAPSHOT-jar-with-dependencies.jar"

## Run via Maven
mvn exec:java

## Available services

- GET [http://localhost:28080/moneytransfer/accounts](http://localhost:28080/moneytransfer/accounts)

- GET [http://localhost:28080/moneytransfer/accounts/8def8bbd-6a72-4ac7-9b88-bb9b64d1d823](http://localhost:28080/moneytransfer/accounts/8def8bbd-6a72-4ac7-9b88-bb9b64d1d823)
Response 
{
    "id": "8def8bbd-6a72-4ac7-9b88-bb9b64d1d823",
    "number": "12312312312123456",
    "balance": 50000.0,
    "currency": "GBP",
    "active": true
}

- POST [http://localhost:28080/moneytransfer/accounts](http://localhost:28080/moneytransfer/accounts)
Payload
{
	"number":"12312312312123456",
	"balance":"50000.0",
	"currency":"GBP"
}

- POST [http://localhost:28080/moneytransfer/transactions/fundstransfer](http://localhost:28080/moneytransfer/transactions/fundstransfer)

Payoad
{
	"source":"9b3d6956-c177-46d0-be0b-b00916d70b3f",
	"target":"8def8bbd-6a72-4ac7-9b88-bb9b64d1d823",
	"amount":"15.0",
	"description":"transfer"
}
Response
{
{
    "id": "37f35d68-0080-4282-9666-afa6c60308a9",
    "source": "9b3d6956-c177-46d0-be0b-b00916d70b3f",
    "target": "8def8bbd-6a72-4ac7-9b88-bb9b64d1d823",
    "amount": 15.0,
    "currencyCode": "GBP",
    "description": transfer,
    "state": "COMPLETED"
}


## Http status
- 200 OK
- 204 Not found
- 400 Bad request
- 409 - Conflict
- 500 Internal Server Error

## Note
- topic not relevant for this task - currency conversion, payment procession when transfer is between counter parties, Ledger management, Approval invocation and fraud detection.  
