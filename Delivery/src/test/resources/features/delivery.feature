Feature: Delivery feature

  Scenario Template: Create a delivery person
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    Then the return must be "200"
    Examples:
      | name | email        | phone   | plate |
      |test  |test@gmail.com|119282737|ABS1306|


  Scenario Template: Delete a delivery person
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I delete the delivery person
    Then the return must be "200"
    Examples:
      | name | email        | phone   | plate |
      |test  |test@gmail.com|119282737|ABS1306|

  Scenario Template: Get a delivery person by id
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I read the delivery person by id
    Then the return must be "200"
    Examples:
      | name | email        | phone   | plate |
      |test  |test@gmail.com|119282737|ABS1306|


  Scenario Template: Get all delivery person
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I read all deliveries person
    Then the return must be "200"
    Examples:
      | name | email        | phone   | plate |
      |test  |test@gmail.com|119282737|ABS1306|


  Scenario Template: Get all delivery person by statua
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I read all deliveries person by "<status>"
    Then the return must be "200"
    Examples:
      | name | email        | phone   |status   | plate |
      |test  |test@gmail.com|119282737|AVAILABLE|ABS1306|
      |test  |test@gmail.com|119282737|BUSY     |ABS1306|
      |test  |test@gmail.com|119282737|OFF_DUTY |ABS1306|


  Scenario Template: Update delivery status and send an order event
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I update the delivery "<status>"
    Then the return must be "404"
    Examples:
      | name | email        | phone   | plate |status   |
      |test  |test@gmail.com|119282737|CBS1306|COMPLETED|

  Scenario Template: Update delivery
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I update the delivery with a "<newName>" and "<newPhone>"
    Then the return must be "200"
    Examples:
      | name  | email         | phone   | plate |newName|newPhone|
      |test1  |test1@gmail.com|119282737|CBS1306|test2  |9827355 |

  Scenario Template: Get tracking clientId
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I get the tracking by "<clientId>"
    Then the return must be "404"
    Examples:
      | name | email        | phone   | plate |clientId                            |
      |test  |test@gmail.com|119282737|ABS1306|c47ed155-df42-48ec-8b1d-597e71997b9a|

  Scenario Template: Tracking delivery by order id
    Given that I create a delivery person with "<name>", "<email>", "<phone>" and "<plate>"
    When I tracking by "<orderId>" id
    Then the return must be "404"
    Examples:
      | name | email        | phone   | plate |orderId                             |
      |test  |test@gmail.com|119282737|ABS1306|31e52a0d-b495-4048-aaaa-2b0d24df96b6|