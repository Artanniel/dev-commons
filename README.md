Dev Commons - Backend Java com Spring Boot / Dev Commons - Java Spring Boot Backend

🇧🇷 Português | 🇺🇸 English

🇧🇷 Português

📋 Visão Geral

Bem-vindo ao Dev Commons, um backend robusto construído com Java Spring Boot, projetado para oferecer uma API RESTful com uma arquitetura em camadas (Controllers, Services e Repository) e integração com o banco de dados Oracle Free. Este projeto é orquestrado com Docker Compose para facilitar o setup e a execução.

🛠️ Tecnologias Utilizadas

Java 11+: Linguagem principal.
Spring Boot: Framework para criação da API REST.
Spring Data JPA: Para acesso ao banco de dados.
Oracle Free: Banco de dados relacional.
Docker e Docker Compose: Para orquestração de containers.
Maven: Gerenciamento de dependências.

📦 Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

Java JDK 11 ou superior
Maven
Docker
Docker Compose
Uma IDE como IntelliJ IDEA ou Eclipse

🚀 Como Iniciar o Projeto

Siga os passos abaixo para configurar e rodar o projeto localmente.
1. Clone o Repositório
   git clone https://github.com/Artanniel/dev-commons.git
   cd meu-projeto

2. Configure o Banco de Dados Oracle Free
   O projeto usa uma imagem Docker do Oracle Free. Edite o arquivo docker-compose.yml na raiz do projeto e defina uma senha para o banco de dados:
   environment:
   ORACLE_PASSWORD: sua_senha

   Substitua sua_senha por uma senha de sua escolha. A mesma senha deve ser configurada no arquivo src/main/resources/application.properties:
   spring.datasource.password=sua_senha

3. Compile o Projeto
   Use o Maven para compilar o projeto e gerar o arquivo JAR:
   mvn clean package

   Isso criará o arquivo target/dev-commons-0.0.1-SNAPSHOT.jar.

4. Suba os Serviços com Docker Compose
   Na raiz do projeto, execute o seguinte comando para iniciar o Oracle Free e a aplicação Spring Boot:
   sudo sh start_app.sh or docker-compose up --build -d
   
   O Oracle Free estará acessível na porta 1521.
   A API Spring Boot estará disponível em http://localhost:8080.

   Para parar os serviços, pressione Ctrl+C ou execute:
   docker-compose down

5. Teste a API
   Com a aplicação rodando, você pode testar os endpoints usando ferramentas como Postman ou cURL. Exemplos:
   Listar Usuários
   curl http://localhost:8080/usuarios

   Retorna a lista de usuários cadastrados.
   Criar Usuário
   curl -X POST http://localhost:8080/usuarios -H "Content-Type: application/json" -d '{"nome":"João","email":"joao@example.com"}'
   
   Cria um novo usuário e retorna os detalhes.

📂 Estrutura do Projeto
   
   meu-projeto/
   ├── src/
   │   ├── main/
   │   │   ├── java/com/example/meuprojeto/
   │   │   │   ├── controller/      # Contém os controllers REST
   │   │   │   ├── model/          # Entidades JPA
   │   │   │   ├── repository/     # Interfaces de acesso ao banco
   │   │   │   ├── service/        # Lógica de negócio
   │   │   ├── resources/
   │   │   │   └── application.properties  # Configurações da aplicação
   ├── Dockerfile                  # Configuração do container da aplicação
   ├── docker-compose.yml          # Orquestração dos serviços
   ├── pom.xml                     # Dependências do Maven
   ├── start_app.sh                # Script de inicialização
   └── README.md                   # Este arquivo

🔧 Resolução de Problemas

   Erro de conexão com o banco: Verifique se o Oracle Free está rodando (docker ps) e se a senha no application.properties coincide com a do docker-compose.yml.
   
   Dependência Oracle JDBC não encontrada: O driver Oracle pode não estar no Maven Central. Baixe-o do site da Oracle e instale-o localmente com:
   mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle.database.jdbc -DartifactId=ojdbc8 -Dversion=19.7.0.0 -Dpackaging=jar
   
   Container não inicia: Certifique-se de que as portas 1521 e 8080 não estão em uso.

🌟 Próximos Passos

   Adicione mais endpoints à API para suportar outras operações CRUD.
   Implemente autenticação com Spring Security.
   Configure testes unitários e de integração com JUnit e MockMvc.
   Integre com um frontend (React, Angular, etc.) para uma aplicação full-stack.

📚 Recursos Adicionais

   Documentação do Spring Boot https://docs.spring.io/spring-boot/documentation.html
   Documentação do Oracle Free https://hub.docker.com/r/gvenzl/oracle-free
   Docker Compose Reference https://docs.docker.com/reference/compose-file/


🇺🇸 English

📋 Overview

Welcome to Dev Commons, a robust backend built with Java Spring Boot, designed to provide a RESTful API with a layered architecture (Controllers, Services, and Repository) and integration with the Oracle Free database. This project is orchestrated with Docker Compose to simplify setup and execution.

🛠️ Technologies Used

Java 11+: Main programming language.
Spring Boot: Framework for building the REST API.
Spring Data JPA: For database access.
Oracle Free: Relational database.
Docker and Docker Compose: For container orchestration.
Maven: Dependency management.

📦 Prerequisites

Before starting, ensure you have the following tools installed:

Java JDK 11 or higher
Maven
Docker
Docker Compose
An IDE like IntelliJ IDEA or Eclipse

🚀 How to Start the Project

Follow the steps below to set up and run the project locally.

1. Clone the Repository
   git clone https://github.com/Artanniel/dev-commons.git
   cd my-project

2. Configure the Oracle Free Database
   The project uses a Docker image of Oracle Free. Edit the docker-compose.yml file in the project root and set a password for the database:
   environment:
   ORACLE_PASSWORD: your_password

   Edit the file start_app.sh in the root directory of the project and replace a password for the database:
   NEW_PASSWORD="sua senha"   

   Replace your_password with a password of your choice. The same password must be configured in the src/main/resources/application.properties file:
   spring.datasource.password=your_password

3. Build the Project
   Use Maven to build the project and generate the JAR file:
   mvn clean package

   This will create the target/dev-commons-0.0.1-SNAPSHOT.jar file.

4. Start Services with Docker Compose
   In the project root, run the following command to start Oracle Free and the Spring Boot application:
   sudo sh start_app.sh or docker-compose up --build -d
     
   Oracle Free will be accessible on port 1521.
   The Spring Boot API will be available at http://localhost:8080.
   
   To stop the services, press Ctrl+C or run:
   docker-compose down

5. Test the API
   With the application running, you can test the endpoints using tools like Postman or cURL. Examples:
   List Users
   curl http://localhost:8080/usuarios

   Returns the list of registered users.
   Create a User
   curl -X POST http://localhost:8080/usuarios -H "Content-Type: application/json" -d '{"nome":"John","email":"john@example.com"}'

Creates a new user and returns the details.
📂 Project Structure

   my-project/
   ├── src/
   │   ├── main/
   │   │   ├── java/com/example/myproject/
   │   │   │   ├── controller/      # Contains REST controllers
   │   │   │   ├── model/          # JPA entities
   │   │   │   ├── repository/     # Database access interfaces
   │   │   │   ├── service/        # Business logic
   │   │   ├── resources/
   │   │   │   └── application.properties  # Application configurations
   ├── Dockerfile                  # Application container configuration
   ├── docker-compose.yml          # Service orchestration
   ├── pom.xml                     # Maven dependencies
   ├── start_app.sh                # Script to start the application
   └── README.md                   # This file

🔧 Troubleshooting

Database connection error: Check if Oracle Free is running (docker ps) and if the password in application.properties matches the one in docker-compose.yml.

Oracle JDBC dependency not found: The Oracle driver may not be in Maven Central. Download it from the Oracle website and install it locally with:
mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle.database.jdbc -DartifactId=ojdbc8 -Dversion=19.7.0.0 -Dpackaging=jar


Container fails to start: Ensure ports 1521 and 8080 are not in use.


🌟 Next Steps

Add more API endpoints to support additional CRUD operations.
Implement authentication with Spring Security.
Set up unit and integration tests with JUnit and MockMvc.
Integrate with a frontend (React, Angular, etc.) for a full-stack application.

📚 Additional Resources

Spring Boot Documentation https://docs.spring.io/spring-boot/documentation.html
Oracle Free Documentation https://hub.docker.com/r/gvenzl/oracle-free
Docker Compose Reference https://docs.docker.com/reference/compose-file/

Feito por Atanniel Fortes! / Made by Artanniel Fortes!
