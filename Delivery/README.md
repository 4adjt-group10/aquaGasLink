# AquaGasLink - Delivery
Um microserviço de gestão de entregas.

# Descrição
Este microserviço é responsável por gerenciar as informações de entrega. Recebe informações via messageria com informações de produto,
cliente, endereço e realiza a entrega dos produtos.

Também realiza o rastreio de entregas e cálculo de rotas, considerando a distância entre o endereço de entrega e a localização do entregador.

O serviço de delivery também gerencia os entregadores, permitindo a criação, atualização, exclusão e consulta de informações.
Além disso, atribui entregadores a entregas específicas.

# Funcionalidades
- **Rastreamento de Entregas**: Permite rastrear entregas por ID do pedido ou ID do cliente, juntamente com latitude e longitude, ou endereço completo (integração com [Google Directions API](https://developers.google.com/maps/documentation/directions/get-directions))
- **Finalização de Entregas**: Permite atualizar o status de uma entrega e envia um evento de pedido para o serviço `order`, para que seja finalizado.
- **Gerenciamento de Entregadores**: Permite a criação, atualização, exclusão e consulta de informações dos entregadores.
- **Atribuição de Entregadores**: Atribui automaticamente entregadores disponíveis à entregas em estado de espera.

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
3. Suba o serviço `rabbitmq`, presente no arquivo `base-compose.yml`. Ele é necessário para o correto funcionamento do serviço.
4. Execute o comando `docker-compose up` para iniciar os contêineres do microserviço e suas dependências.
5. Para visualizar a documentação da API, acesse: `http://localhost:8086/swagger-ui/index.html`.

# Endpoints
- **POST `/delivery/tracking/{orderId}`**: Rastreia a entrega por ID do pedido.
- **PUT `/delivery/finish/{deliveryId}`**: Finaliza a entrega e atualiza o status.
- **GET `/delivery/track-by-client/{clientId}`**: Rastreia a entrega por ID do cliente.