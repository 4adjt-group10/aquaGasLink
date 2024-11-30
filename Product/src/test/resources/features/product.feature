Feature: Product

  Scenario Template:Register a product
    Given that I create some products with "<name>" and "<code>"
    Then the return must be "200"
    Examples:
      |name         |code     |
      |product 1    |code 1   |
      |product 2    |code 2   |

  Scenario Template:Register products
    Given that I create some products with "<name1>" and "<code1>" and "<name2>" and "<code2>"
    Then the return must be "200"
    Examples:
      |name1        |code1    |name2        |code2    |
      |product 1    |code 1   |product 2    |code 2   |
      |product 3    |code 3   |product 4    |code 4   |

  Scenario Template:Read a product by id
    Given that I create some products with "<name>" and "<code>"
    When I look for a product by id
    Then the return must be "200"
    Examples:
      | name        |code     |
      |product 7    |code 7   |

  Scenario Template:Read a product by name
    Given that I create some products with "<name>" and "<code>"
    When I look for a product by name
    Then the return must be "200"
    Examples:
      |name         |code     |
      |product 9    |code 9   |

  Scenario Template:Read a product by productCode
    Given that I create some products with "<name>" and "<code>"
    When I look for a product by productCode
    Then the return must be "200"
    Examples:
      |name          |code      |
      |product 10    |code 10   |
      |product 12    |code 12   |

  Scenario Template:List all
    Given that I create some products with "<name>" and "<code>"
    When I list all products
    Then the return must be "200"
    Examples:
      |name          |code      |
      |product 13    |code 17   |
      |product 14    |code 18   |

  Scenario Template:Delete
    Given that I create some products with "<name>" and "<code>"
    When I delete the product
    Then the return must be "200"
    Examples:
      |name          |code      |
      |product 13    |code 17   |