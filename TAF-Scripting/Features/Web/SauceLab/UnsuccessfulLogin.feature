@webTest
Feature: Unsuccessful Login

  @Web
  Scenario Outline: User is unable to login successfully
    Given I launched sauce lab application
    When I enter username as "<username>"
    And I enter password as "<password>"
    Then I click on sauce lab log in button
    Then I validate login error message
    Examples:
      | username      | password |
      | standard_user | secret   |
      | locked_user   | secret   |

#  @Web
#  Scenario: User is unable to login successfully
#    Given I launched sauce lab application
#    When I enter username
#    And I enter password
#    Then I click on sauce lab log in button
#    Then I validate login error message