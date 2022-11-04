@all
Feature: Testing a REST API
  Should be able to submit GET, POST and DELETE requests to a web service,
  represented by WireMock

  @correct
  Scenario: Data uploads to the web service
    When uploads metadata on the service
    Then the server should return metadata id and success status

  @correct
  Scenario: Data gets from the web service
    When gets metadata from the service
    Then the server should return metadata and success status

  @correct
  Scenario: Data delete from the web service
    When delete metadata from the service
    Then the server should return deleted ids and success status
