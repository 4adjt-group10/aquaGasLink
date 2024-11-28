# aquaGasLink
A microservice application for water and gas delivery and tracking
endereço do rabbit http://localhost:15672/ 
usuario e senha guest
obs se faz necessario um csv ao subir o projeto nem que o mesmo esteja somente com cabeçalho
# Instruções para Enviar Arquivo CSV

## Enviando o Arquivo CSV via Postman

1. Abra o Postman e crie uma nova requisição.
2. Selecione o método `POST`.
3. Digite a URL: `http://localhost:8081/api/upload_csv`.
4. Vá para a aba `Body`.
5. Selecione a opção `form-data`.
6. Adicione uma chave chamada `file` e selecione `File` no tipo de entrada.
7. Clique em `Choose Files` e selecione o arquivo CSV que você deseja enviar.
8. Clique em `Send` para enviar a requisição.

## Enviando o Arquivo CSV via `curl`

No terminal, você pode usar o seguinte comando `curl` para enviar o arquivo CSV:

```bash
curl -F 'file=@caminho/do/seu/arquivo.csv' http://localhost:8081/api/upload_csv
