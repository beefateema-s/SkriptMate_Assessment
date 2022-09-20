Feature: Login

  @Test-1 @Web @Demo @Training
  Scenario Outline: Verify user not able to login facebook using invalid credentials
    Given I launched facebook application
    When I enter username as "<username>" and password as "<password>"
    Then I click on log in button
    And I should not login
    Examples:
    |username||password|
    |test@fb.com||Test123|

