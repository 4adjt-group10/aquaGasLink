# Serviço de gerenciamento de pedidos
Este serviço é responsável por gerenciar os pedidos de uma distribuidora de água e gás. 
Ele é responsável por criar, atualizar, excluir e listar pedidos.

## Tecnologias
- Java 17
- Spring Boot 2.5.5
- Maven
- PostgreSQL
- Docker compose
- JUnit 5
- Mockito
- Swagger
- Jacoco

### Requisitos

Centralizará o processamento de todos os pedidos, desde a criação até a conclusão. 
Isso inclui receber pedidos dos clientes, processar pagamentos (se aplicável), e coordenar com o 
microsserviço de logística de entrega para garantir a entrega eficiente dos produtos.

Tecnologias e Abordagens:
• Spring Boot para a estrutura do serviço.
• Spring Data JPA para manipulação de dados dos pedidos.
• Spring Cloud Stream para comunicação baseada em eventos com outros serviços, melhorando a
coordenação de processos de negócios complexos.