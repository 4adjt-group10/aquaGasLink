Feature: Delivery feature

  Scenario Template: Create a delivery person
    Given that I create a delivery person with "<name>", "<email>" and "<phone>"
    Then the return must be "200"
    Examples:
      | name | email        | phone   |
      |test  |test@gmail.com|119282737|


  Scenario Template: Delete a delivery person
    Given that I create a delivery person with "<name>", "<email>" and "<phone>"
    When I delete the delivery person
    Then the return must be "200"
    Examples:
      | name | email        | phone   |
      |test  |test@gmail.com|119282737|

  Scenario Template: Get a delivery person by id
    Given that I create a delivery person with "<name>", "<email>" and "<phone>"
    When I read the delivery person by id
    Then the return must be "200"
    Examples:
      | name | email        | phone   |
      |test  |test@gmail.com|119282737|

