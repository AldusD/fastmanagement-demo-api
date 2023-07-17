# Fast Management demo API

[Read this page in english](https://github.com/AldusD/fastmanagement-demo-api/blob/main/README.md)

[Deploy](https://fastmanagement-demo-api.onrender.com/) [|](https://github.com/AldusD/fastmanagement-demo-api) [Repositório Frontend](https://github.com/AldusD/fastmanagement-demo-interface)

Fast Management: Escolhas inteligentes, Decisões rápidas 

## Sobre
Fast Management é um aplicativo simples utilizado para decidir entre candidatos. Esse projeto teve como objetivo consolidar os aprendizados nas seguintes tecnologias:
- Testes unitários com Junit5, Mockito e MockMvc
- Criação de API Rest com Spring Boot
- POO e fundamentos de Java  
- Circuito CI/CD e deploy com Dockerfile

## Como executar para desenvolvimento

1. Clone esse repositório
2. Instale todas as dependências 
3. Inicie a API com Maven
```node
  ./mvnw spring-boot:run
```
## Documentação da API
Todas as rotas iniciam com /api/v1 o mesmo vale para o url do deploy

- Rota de Health:
  
    ```node
    GET /api/v1/health

    Retorna status 200 e verifica se a API está ativa
  ```

- Rotas de Job application:

    ```node
    POST /api/v1/hiring/start

    Precisa de um parâmetro string 'nome', verifica conflito e returna o id do candidato.    
  ```

    ```node
    POST /api/v1/hiring/schedule
    
    Precisa de um parâmetro number 'codCandidato' que se refere a um candidato existente, verifica o candidato.
  ```

    ```node
    POST /api/v1/hiring/disqualify

    Precisa de um parâmetro number 'codCandidato' que se refere a um candidato existente, verifica o candidato. 
  ```

    ```node
    POST /api/v1/hiring/approve

    Precisa de um parâmetro number 'codCandidato' que se refere a um candidato existente, verifica o candidato. 
  ```

    ```node
    POST /api/v1/hiring/status/candidate/{codCandidato}

    Precisa de um parâmetro de rota number 'codCandidato' que se refere a um candidato existente, verifica o candidato.
  ```

    ```node
    GET /api/v1/hiring/all

    Retorna todos candidatos dentro de um atributo 'application'.
  ```

    ```node
    GET /api/v1/hiring/approved

    Retorna candidatos aprovados dentro de um atributo 'approved'.
  ```
  
