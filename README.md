#Microservices fundamentals EPAM training

#Overview
Contain 3 microservices related to domain business logic based on Java 11, Spring Boot 2.7.x: Resource processor service, Resource file service, Song metadata service.
They use Spring MVC REST and asynchronous (RabbitMQ based) requests to communicate. Queries from front and from Resource processor service to two other service go through API gateway (Spring Cloud Gateway) to resolve real address and use aliases that resolve by Spring Cloud Discovery server(Netflix Eureka).
Additionally microservices have nessesary logic to use Spring Cloud Config server for updating properties from central repo.

#Preliminary testing strategy for resource service
It seems should cover approximately 70-80 percent all microservices by unit tests. 
It mandatory for the service layer and all under laing layers (use for ex. junit and mockito). 
All controller layer endpoints should cover by integration tests and preferably use positive and negative scenarios (spring boot test plus wire mock or testcontainers). 
Also for all synchronous and asynchronous interaction scenarios shoud prepare contract tests (use spring cloud contract for ex.) 
It does'n take a lot of time, and would make the contract more enforceable. 
After deployment in dev envoronment should cover all externall API by e2e test (with perfomance testing).

# How to use
Download the repo and use docker-compose.yaml to build and run the project. After you need create two buckets: with sotrageType "STAGING" and "PERMANENT" (see 4-curls-example-add-delete-storage.txt in the root folder).
After you shoud use other example curl-*.txt files to operate with mp3 resourses.