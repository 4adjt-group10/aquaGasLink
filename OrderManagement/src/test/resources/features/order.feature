Feature: Order management

  Scenario Template:Register a order
    Given that I create some products with "<name>" and "<code>"
    Then the return must be "200"
    Examples:
      |name         |code     |
      |product 1    |code 1   |