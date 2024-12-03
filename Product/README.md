# AquaGasLink - Product
Um microserviço de gestão de produtos.

# Descrição
Este microserviço é responsável por gerenciar as informações de produtos. Permite o cadastro, atualização, consulta e exclusão de produtos, além de possibilitar o upload de arquivos CSV para cadastro em massa.

# Funcionalidades
- **Cadastro de Produtos**: Permite o cadastro de novos produtos.
- **Cadastro em Massa de Produtos**: Permite o cadastro de uma lista de novos produtos.
- **Consulta de Produtos**: Permite a consulta de produtos por ID, nome ou código do produto.
- **Listagem de Produtos**: Permite listar todos os produtos cadastrados.
- **Exclusão de Produtos**: Permite a exclusão de produtos por ID.
- **Upload de Arquivo CSV**: Permite o upload de arquivos CSV para cadastro em massa de produtos.
- **Início de Serviço em Lote**: Permite iniciar o serviço em lote para processar os arquivos CSV.

# Tecnologias Utilizadas
#### Desenvolvimento
- **Java 17**
- **Spring Data**
- **Spring Boot**
- **Spring Batch**
- **Maven**
- **PostgreSQL**
- **Spring Cloud Stream**
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
4. Para visualizar a documentação da API, acesse: `http://localhost:8081/swagger-ui/index.html`.

# Endpoints
#### Gerenciamento de produtos
- **POST `/product/register`**: Cadastra um novo produto.
- **POST `/product/register-products`**: Cadastra uma lista de novos produtos.
- **GET `/product/find-id/{id}`**: Consulta um produto por ID.
- **GET `/product/find-name/{name}`**: Consulta um produto por nome.
- **GET `/product/find-product/{productcode}`**: Consulta um produto por código do produto.
- **GET `/product/find-all`**: Lista todos os produtos cadastrados.
- **DELETE `/product/{id}`**: Exclui um produto por ID.
#### Upload de Arquivo CSV
- **POST `/batch/upload_csv`**: Faz o upload de um arquivo CSV.

> É necessário que o nome do arquivo obedeça o padrão `product.csv`.

> Além disso, é necessário seguir o padrão do arquivo, separando os campos por vírgula e respeitando a ordem dos campos: 
>`description`, `name`, `price`, `productCode`, `stock`.

- **GET `/batch/start`**: Inicia o serviço em lote para processar arquivos CSV.

# Instruções para Enviar Arquivo CSV
Para enviar um arquivo CSV para cadastro em massa de produtos, você pode usar o Postman ou o `curl`.

## Enviando o Arquivo CSV via Postman
1. Abra o Postman e crie uma nova requisição.
2. Selecione o método `POST`.
3. Digite a URL: `http://localhost:8081/batch/upload_csv`.
4. Vá para a aba `Body`.
5. Selecione a opção `form-data`.
6. Adicione uma chave chamada `file` e selecione `File` no tipo de entrada.
7. Clique em `Choose Files` e selecione o arquivo CSV que você deseja enviar.
8. Clique em `Send` para enviar a requisição.

## Enviando o Arquivo CSV via `curl`
No terminal, você pode usar o seguinte comando `curl` para enviar o arquivo CSV:
```bash
curl -F 'file=@caminho/do/seu/arquivo.csv' http://localhost:8081/batch/upload_csv
```

## Enviando o Arquivo CSV via Postman
1. Abra o Postman e crie uma nova requisição.
2. Selecione o método `POST`.
3. Digite a URL: `http://localhost:8081/api/upload_csv`.
4. Vá para a aba `Body`.
5. Selecione a opção `form-data`.
6. Adicione uma chave chamada `file` e selecione `File` no tipo de entrada.
7. Clique em `Choose Files` e selecione o arquivo CSV que você deseja enviar.
8. Clique em `Send` para enviar a requisição.

> [!WARNING]  
> Para o correto funcionamento do serviço é necessário que exista ao menos um arquivo CSV na pasta raiz do projeto. 
> Isso se deve ao fato do Spring Batch necessitar de um arquivo para criar a instância do bean utilizado. 