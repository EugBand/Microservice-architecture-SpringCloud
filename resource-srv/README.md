#Resource file service

#Overview
This repository contains set of deploy able components for resource file service,
based on Java 11, Spring Boot 2.7.x and other Spring related projects.
This service allows get/put to AWS S3 bucket and related information from/to H2 DB

#Preliminary testing strategy
It seems should cover approximately 70-80 percent all microservices by unit tests.
It mandatory for the service layer and all under laing layers (use for ex. junit and mockito).
All controller layer endpoints should cover by integration tests and preferably use positive and negative scenarios (spring boot test plus wire mock or testcontainers).
Also for all synchronous and asynchronous interaction scenarios shoud prepare contract tests (use spring cloud contract for ex.)
It does'n take a lot of time, and would make the contract more enforceable.
After deployment in dev envoronment should cover all externall API by e2e test (with perfomance testing).
