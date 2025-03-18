# **API de Cálculo de Impostos**

> API RESTful para gerenciar e calcular impostos, com autenticação e autorização utilizando Spring Security e JWT.

---

## **Descrição**

Esta API foi desenvolvida para gerenciar diferentes tipos de impostos e realizar cálculos com base no tipo de imposto e no valor base fornecido. A API é segura, utilizando autenticação e autorização com Spring Security e JWT.

**Observação:** a API conta co logs do sistema, fique atento ao console

---

## **Funcionalidades**

- **Gerenciamento de Tipos de Impostos**:
    - Listar todos os tipos de impostos.
    - Cadastrar novos tipos de impostos (restrito a ADMIN).
    - Obter detalhes de um tipo de imposto específico.
    - Excluir um tipo de imposto (restrito a ADMIN).

- **Cálculo de Impostos**:
    - Calcular o valor do imposto com base no tipo e no valor base (restrito a ADMIN).

- **Autenticação e Autorização**:
    - Registro de usuários.
    - Login e geração de tokens JWT.
    - Controle de acesso baseado em papéis (USER e ADMIN).

---

## **Tecnologias Utilizadas**

- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **JWT (JSON Web Token)**
- **JUnit**
- **Jacoco**
- **Swagger**
- **Postgres**
- **Docker**
- **GitHub actions**
- **SL4J (Logs)**

---

## **Pré-requisitos**

Antes de começar, certifique-se de ter os seguintes itens instalados:

- **Java 17** ou superior
- **Maven** (para gerenciamento de dependências)
- **Git** (para clonar o repositório)
- **Docker**

---

## **Instalação**

1. Clone o repositório e entre no repositório:
   ```bash
   git clone https://github.com/mathde-s/desafio-imposto & cd desafio_imposto
   ```
2. Altere o arquivo application.proprieties e coloque as configurações do banco de dados
   
3. Faça a instalação de dependências pelo maven 
    ```bash
    mvn clean install
    ```
4. Agora rode o projeto
    ```bash
    mvn spring-boot:run
    ```
---

### Documentação da API com swagger

[Link da doc](http://localhost:8080/swagger-ui.html)
