# AquaGasLink
Uma aplicação de microserviços para gerenciamento e rastreio de pedidos de água e gás em tempo real.

# Descrição
O objetivo do AquaGasLink é fornecer uma solução de gestão de pedidos de água e gás em tempo real, permitindo que os clientes
possam rastrear suas entregas e os entregadores possam gerenciar suas rotas de entrega, obtendo o melhor caminho até o cliente.

Utilizando uma arquitetura de microsserviços, o AquaGasLink é composto por quatro serviços principais:
- **[Client](https://github.com/4adjt-group10/aquaGasLink/tree/main/Client)**: Responsável por gerenciar os clientes.
- **[Product](https://github.com/4adjt-group10/aquaGasLink/tree/main/Product)**: Responsável por gerenciar o estoque de produtos disponíveis para venda. Com upload de produtos via CSV com Spring Batch.
- **[Order](https://github.com/4adjt-group10/aquaGasLink/tree/main/OrderManagement)**: Responsável por gerenciar os pedidos dos clientes. Centralizando as informações de produto e cliente, e disparando o evento de pedido para o serviço de entrega.
- **[Delivery](https://github.com/4adjt-group10/aquaGasLink/tree/main/Delivery)**: Responsável por gerenciar as entregas dos pedidos. Com rastreamento de entregas e cálculo de rotas com [Google Directions API](https://developers.google.com/maps/documentation/directions/get-directions).
> Para ter mais detalhes de cada um dos serviços, acesse o readme de cada um deles, disponíveis nos links acima.

# Funcionalidades
- **Rastreamento de Entregas**: Permite rastrear entregas por ID do pedido ou ID do cliente.
- **Finalização de Entregas**: Permite atualizar o status de uma entrega e enviar um evento de pedido.
- **Gerenciamento de Entregadores**: Permite a criação, atualização, exclusão e consulta de informações dos entregadores.
- **Atribuição de Entregadores**: Permite atribuir entregadores a entregas específicas.

# Tecnologias Utilizadas
#### Desenvolvimento
- **Java 17**
- **Spring Data**
- **Spring Boot**
- **Spring Batch**
- **Maven**
- **PostgreSQL**
- **Spring cloud stream**
- **RabbitMQ**
- **Docker Compose**
- **Swagger**
#### Testes
- **JUnit 5**
- **Mockito**
- **Cucumber**
- **JaCoCo**
- **Rest Assured**

# Como Executar
1. Certifique-se de ter o Java, Maven e Docker Compose instalados em sua máquina.
2. Navegue até o diretório raiz do projeto.
3. Execute o comando `docker-compose -f base-compose.yaml up` para iniciar os contêineres dos microserviços, bancos de dados e messageria.
> [!TIP]
> Para manter o funcionamento do terminal, pode adicionar o comando `-d`, utilizando dessa forma: `docker-compose -f base-compose.yaml up -d`
4. Os serviços estarão disponíveis em:
   - Client: `http://localhost:8082/swagger-ui/index.html`.
   - Product: `http://localhost:8081/swagger-ui/index.html`.
   - Order:`http://localhost:8080/swagger-ui/index.html`.
   - Delivery: `http://localhost:8086/swagger-ui/index.html`.
5. Visualização das filas do RabbitMQ: `http://localhost:15672/`
   - (Usuário: `guest`, Senha: `guest`).
6. Tendo os serviços de pé, seguir a seguinte ordem, simulando a utilização do sistema:
   - 6.1 Cadastrar um cliente em ```POST /client```.
   - 6.2 Ao rodar o serviço de produtos, alguns devem ser cadastrados automaticamente.
   - 6.3 Realizar um pedido utilizando um id de cliente válido, um id de produto válido, quantidade e preço em ```POST /order/create```.
   - 6.4 Após a criação do pedido, o serviço de *OrderManagement* irá disparar eventos para os serviços de *Client* e *Product*, para validar as informações recebidas.
   - 6.5 Após receber a validação, o serviço de *OrderManagement* irá disparar um evento para o serviço de *Delivery*, que irá criar uma nova entrega com status `PENDING`.
   - 6.6 A cada minuto o serviço de *Delivery* irá verificar se existem entregas pendentes e irá atribuir um entregador disponível para a entrega. Com isso o status da entrega irá mudar para `IN_PROGRESS` e o status do entregador mudará para `BUSY`.
   - 6.7 Com a entrega em andamento, iniciar rastreio por meio do endpoint ```POST /delivery/tracking/{orderId}```, passando o id do pedido(orderId) e latitude + longitude ou o endereço completo. (É necessário apenas um dos dois).
   - 6.8 Nesse ponto, o possível front-end do sistema iria atualizar a posição do entregador e a rota até o cliente. Disparando requisições periodicamente para o endpoint ```GET /delivery/track-by-client/{clientId}```.
   - 6.9 Após a entrega ser realizada, o entregador irá finalizar a entrega por meio do endpoint ```PUT /delivery/finish/{deliveryId}```. Com isso o status da entrega irá mudar para `DELIVERED` e o status do entregador mudará para `AVAILABLE`. Isso irá disparar um evento para o serviço de *OrderManagement* que irá atualizar o status do pedido para `COMPLETED`. Caso o entregador não consiga realizar a entrega, o status da entrega será alterado para `CANCELLED`, o status do entregador mudará para `AVAILABLE` e o evento disparado irá mudar o status de Order para `CANCELLED`.
> [!TIP] 
> Para auxiliar na obtenção de latitude e longitude, utilize [Coordenadas-gps](https://www.coordenadas-gps.net/) ou o [Google Maps](https://www.google.com/maps).
7. Após testar, poderá derrubar todos os serviços ativos com o comando: `docker-compose -f base-compose.yaml down`, ou caso não possa inserir o comando no terminal, utilize o atalho `ctrl + c` ou `ctrl + shift + c`
