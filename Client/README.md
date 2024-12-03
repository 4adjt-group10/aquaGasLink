# AquaGasLink - Client
Um microserviço de gestão de clientes.

# Descrição
Este microserviço é responsável por gerenciar as informações dos clientes, incluindo criação, atualização, exclusão e consulta de dados de clientes.

# Funcionalidades
- **Criação de Clientes**: Permite a criação de novos registros de clientes.
- **Atualização de Clientes**: Permite a atualização das informações de clientes existentes.
- **Exclusão de Clientes**: Permite a exclusão de registros de clientes.
- **Consulta de Clientes**: Permite a consulta de informações detalhadas de clientes.

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
3. Suba a rede `rabbitmq_go_net` e o serviço `rabbitmq`, presente no arquivo `base-compose.yml`. Ele é necessário para o correto funcionamento do serviço.
4. Execute o comando `docker-compose up` para iniciar os contêineres do microserviço e suas dependências.
5. Para visualizar a documentação da API, acesse: `http://localhost:8082/swagger-ui/index.html`.

# Endpoints
- **POST `/client`**: Cria um novo cliente.
- **GET `/client/{id}`**: Retorna as informações de um cliente específico.
- **GET `/client/all`**: Lista todos os clientes.
- **PUT `/client/{id}`**: Atualiza as informações de um cliente específico.
- **DELETE `/client/{id}`**: Exclui um cliente específico.