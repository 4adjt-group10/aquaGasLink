Feature: Client feature

  Scenario Template:Register a client
    Given that I create a client with "<cpf>"
    Then the return must be "201"
    Examples:
      | cpf       |
      |12345628900|
      |12345638901|


  Scenario Template:Read a client by id
    Given that I create a client with "<cpf>"
    When I look for a client by id
    Then the return must be "200"
    Examples:
      | cpf       |
      |12345674902|
      |12345675903|

  Scenario Template:Read all clients
    Given that I create a client with "<cpf>"
    When I look for all clients
    Then the return must be "200"
    Examples:
      | cpf       |
      |12645678904|
      |12745678905|

  Scenario Template:Delete clients
    Given that I create a client with "<cpf>"
    When I delete the client
    Then the return must be "204"
    Examples:
      | cpf       |
      |12385678908|
      |12395678909|

  Scenario Template:Update clients
    Given that I create a client with "<cpf>"
    When I update the client with "<name>"
    Then the return must be "200"
    Examples:
      | cpf       |name |
      |12345672912|name1|
      |12345677913|name2|
