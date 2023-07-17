# Fast Management demo API

[Leia esta página em português](https://github.com/AldusD/fastmanagement-demo-api/blob/main/README-pt.md)

[App Deploy](https://fastmanagement-demo-api.onrender.com/) [|](https://github.com/AldusD/fastmanagement-demo-api) [Frontend Repository](https://github.com/AldusD/fastmanagement-demo-interface)

Fast Management: Smart choices, fast resolution 

## About
Fast Management is a clean app used to decide between candidates. This project was used to consolidating the knowledge in the following technologies:
- Unit tests with Junit5, Mockito and MockMvc
- Creating Rest API with Spring Boot
- OOP and Java basics  
- CI/CD circtuits deploying from a Dockerfile

## How to run for development

1. Clone this repository
2. Install all dependencies 
3. Start the API with Maven
```node
  ./mvnw spring-boot:run
```
## API documentation
Every route starts from the path /api/v1 this is the same for the deploy url

- Health route:
  
    ```node
    GET /api/v1/health

    Return status 200 and verifies if API is on
  ```

- Job application routes:

    ```node
    POST /api/v1/hiring/start

    Needs a string param 'nome', will check for conflict and return candidate id.    
  ```

    ```node
    POST /api/v1/hiring/schedule
    
    Needs a number param 'codCandidato' that refers to an existing candidate, will validate candidate.
  ```

    ```node
    POST /api/v1/hiring/disqualify

    Needs a number param 'codCandidato' that refers to an existing candidate, will validate candidate. 
  ```

    ```node
    POST /api/v1/hiring/approve

    Needs a number param 'codCandidato' that refers to an existing candidate, will validate candidate. 
  ```

    ```node
    POST /api/v1/hiring/status/candidate/{codCandidato}

    Needs a number path param 'codCandidato' that refers to an existing candidate, will validate candidate. 
  ```

    ```node
    GET /api/v1/hiring/all

    Return all candidates inside 'application' attribute
  ```

    ```node
    GET /api/v1/hiring/approved

    Return approved candidates inside 'approved' attribute
  ```
  
