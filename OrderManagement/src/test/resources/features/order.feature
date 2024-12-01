Feature: Order management

  Scenario Template:Register a order
    Given that I create a order with "<product>"
    Then the return must be "200"
    Examples:
      | product |
      |product1 |
      |product2 |

  Scenario Template:Read a order by orders id
    Given that I create a order with "<product>"
    When I look for a order by id
    Then the return must be "200"
    Examples:
      | product |
      |product 5 |
      |product 6 |


  Scenario Template:Read all orders by client id
    Given that I create a order with "<product>"
    When I list all orders by client id
    Then the return must be "200"
    Examples:
      | product |
      |product 5 |
      |product 6 |

  Scenario Template:Update order
    Given that I create a order with "<product>"
    When I update the order with <quantity> and <price>
    Then the return must be "200"
    Examples:
      |product   |quantity|price  |
      |product 5 |10      |15.00   |
      |product 6 |15      |20.00   |

  Scenario Template:Update order status
    Given that I create a order with "<product>"
    When I update the order "<status>"
    Then the return must be "200"
    Examples:
      |product   |status     |
      |product 5 |IN_PROGRESS|
      |product 6 |ERROR      |



