@apiTest-2
Feature: Running api test

  @TestGet
  Scenario: Sample GET method API test
    Given I have sample "Get" API
    When I send a "Get" Request with "DemoSample" properties
#    When I send a "Get" Request with "DemoSample" properties with dynamic parameters
#      | TouristId | 149428 |
    Then I should get response code 200
    And the response should contain:
      | JsonPath   | ExpectedVal |
      | data.id[1] | 149482      |
    Then I save value of json path "data.id[1]" as a variable
    When I send a "Get" Request with "DemoSample2" properties with dynamic parameters