@webTest
Feature: Successful Purchase

  @Web
  Scenario Outline: User is able to purchase items successfully
    Given I launched sauce lab application
    When I enter username as "<username>"
    And I enter password as "<password>"
    Then I click on sauce lab log in button
    When I select filter as "<productFilter>"
    Then I validate selected filter
    When I select items
      | Sauce Labs Backpack               |
      | Test.allTheThings() T-Shirt (Red) |
      | Sauce Labs Fleece Jacket          |
    And I view my shopping cart
    And I remove item from cart
      | Test.allTheThings() T-Shirt (Red) |
    Then I validate items in cart
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |
    And I click on checkout
    And I input my details as "<firstName>", "<lastName>" and "<zipCode>"
    And select continue
    Then I validate total price
    And I click on finish
    Then I validate order successful message
    Examples:
      | username      | password     | productFilter       | firstName | lastName | zipCode |
      | standard_user | secret_sauce | Price (low to high) | Bee       | Fateema  | 123456  |