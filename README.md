#Microservices fundamentals EPAM training

#Overview
Contain 3 microservices related to domain business logic based on Java 11, Spring Boot 2.7.x: Resource processor service, Resource file service, Song metadata service.
They use REST and asynchronous (RabbitMQ based) requests to communicate. Queries from front and from Resource processor service to two other service go through API gateway (Spring Cloud Gateway) to resolve real address and use aliases that resolve by Spring Cloud Discovery server(Netflix Eureka).
Additionally microservices have nessesary logic to use Spring Cloud Config server for updating properties from central repo.

