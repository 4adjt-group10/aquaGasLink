Feature: Order management

  Scenario Template:Register a order
    Given that I create a order with "<product>"
    Then the return must be "200"
    Examples:
      | product |
      |product1 |
      |product2 |

  Scenario Template:Read a order by id
    Given that I create a order with "<product>"
    When I look for a order by id
    Then the return must be "200"
    Examples:
      | product |
      |product3 |
      |product4 |


