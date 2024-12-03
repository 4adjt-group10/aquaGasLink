# AquaGasLink - OrderManagement
Um microserviço de gestão de pedidos.

# Descrição
Este microserviço é responsável por gerenciar as informações de pedidos.

Cria pedidos por meio de requisições HTTP e envia informações de pedidos para o serviço de entrega por meio de eventos.

Além disso, valida as informações de produtos e clientes antes do envio para delivery por meio de eventos direcionados
aos serviços responsáveis, garantindo que os pedidos sejam criados corretamente.

# Funcionalidades
- **Criação de Pedidos**: Permite a criação de novos registros de pedidos.
- **Atualização de Pedidos**: Permite a atualização das informações de pedidos existentes.
- **Atualização de Status de Pedidos**: Permite a atualização do status de um pedido.
- **Consulta de Pedidos**: Permite a consulta de informações detalhadas de pedidos.
- **Listagem de Pedidos por Cliente**: Permite listar todos os pedidos de um cliente específico.

# Tecnologias Utilizadas
#### Desenvolvimento
- **Java 17**
- **Spring data**
- **Spring Boot**
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
> Recomendamos fortemente que utilize o arquivo [`base-compose.yml`](https://github.com/4adjt-group10/aquaGasLink) para subir o ambiente completo com todos os serviços,
> visto que para o correto funcionamento da aplicação é necessário a comunicação via messageria entre os serviços.
1. Certifique-se de ter o Java, Maven e Docker instalados em sua máquina.
2. Navegue até o diretório raiz do projeto.
3. Execute o comando `docker-compose up` para iniciar os contêineres do microserviço e suas dependências.
4. Para visualizar a documentação da API, acesse: `http://localhost:8080/swagger-ui/index.html`.

# Endpoints
- **POST `/order/create`**: Cria um novo pedido.
- **GET `/order/search/{id}`**: Retorna as informações de um pedido específico.
- **GET `/order/list-all/{clientId}`**: Lista todos os pedidos de um cliente específico.
- **PUT `/order/update/{id}`**: Atualiza as informações de um pedido específico.
- **PATCH `/order/update-status/{id}`**: Atualiza o status de um pedido específico.