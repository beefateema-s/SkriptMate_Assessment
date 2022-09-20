@apiTest
Feature: Running all api tests

  @TestGet
  Scenario: Sample GET method API test
    Given I have sample "Get" API
    When I send a "Get" Request with "SampleGETApi" properties
    Then I should get response code 200

  @TestPost
  Scenario: Sample POST method API test
    Given I have sample "Post" API
    When I send a "Post" Request with "SamplePOSTApi" and "PostApiJsonReq"
    Then I should get response code 201
    And the response should contain:
      | JsonPath | ExpectedVal   |
      | name     | morpheus      |
      | job      | zion resident |

  @TestPut
  Scenario: Sample PUT method API test
    Given I have sample "Put" API
    When I send a "Put" Request with "SamplePUTApi" and "PutApiJsonReq"
    Then I should get response code 200
    And the response should contain:
      | JsonPath | ExpectedVal |
      | job      | Kochi       |

  @TestDelete
  Scenario: Sample DELETE method API test
    Given I have sample "Delete" API
    When I send a "Delete" Request with "SampleDELETEApi" properties
    Then I should get response code 204

  @TestGet2
  Scenario: Sample GET method API test with query parameters
    Given I have sample "Get" API
    When I define request value "page" as "2"
    When I send a "Get" Request with "SampleGETApi" properties with query parameters
    Then I should get response code 200
    And the response should contain:
      | JsonPath | ExpectedVal |
      | page     | 2           |

  @TestPatch
  Scenario: Sample PATCH method API test
    Given I have sample "Patch" API
    When I send a "Patch" Request with "SamplePATCHApi" and "PatchApiJsonReq"
    Then I should get response code 200
    And the response should contain:
      | JsonPath | ExpectedVal |
      | job      | leader      |














    #  @TestPost2
#  Scenario: Sample urlencoded POST method API test
#    Given I have sample "Post" API
#    When I send a "Post" urlencoded Request with "SampleURLPOSTApi" and "PostApiJsonReq"
#    Then I should get response code 201
#    And the response should contain:
#      | JsonPath | ExpectedVal |
#      | name     | morpheus    |
#      | job      | leader      |