# CRUD AWS - Sistemas Distribuídos 🚀

Este projeto é uma API REST robusta desenvolvida para a disciplina de **Sistemas Distribuídos**. A aplicação foca em segurança, persistência e conteinerização, utilizando chaves assimétricas (**RSA**) para autenticação **JWT** e **Docker** para orquestração de serviços na nuvem (**AWS**).

---

## 🛠️ Tecnologias Utilizadas

* **Java 25** (OpenJDK) & **Spring Boot 4.x**
* **Spring Security** com **OAuth2 Resource Server**
* **JWT (JSON Web Tokens)** com criptografia **RSA (2048 bits)**
* **PostgreSQL 18** (Base de dados relacional via Docker)
* **Docker & Docker Compose** (Containerização e Orquestração)
* **SpringDoc OpenAPI (Swagger)** (Documentação interativa e testes)
* **Hibernate/JPA** (Persistência de dados com PostgreSQL Dialect)

---

## 🏗️ Arquitetura do Projeto

A aplicação utiliza uma arquitetura baseada em containers isolados, garantindo a paridade entre o ambiente de desenvolvimento local e o de produção na AWS EC2.

### Segurança com RSA
Diferente do JWT comum (HMAC), este projeto utiliza um par de chaves **Pública/Privada**:
1.  **Chave Privada:** Mantida no servidor para assinar os tokens no momento do login.
2.  **Chave Pública:** Usada pelo Resource Server para validar a integridade e autenticidade do token em cada requisição protegida.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
* Docker e Docker Compose instalados.
* Git.

### 1. Clonar o Repositório
```
git clone https://github.com/Fariasartuur/aws-sd.git
cd aws-sd
```

### 2. Configurar Variáveis de Ambiente

Crie um arquivo .env na raiz do projeto:
```
DB_NAME=crud_db
DB_USER=postgres
DB_PASSWORD=password123
```

### 3. Gerar as Chaves RSA (Formato PKCS#8)

As chaves são sensíveis e não devem ser versionadas. Gere-as diretamente no ambiente de execução:
```
docker run --rm -v "$(pwd):/export" alpine sh -c "apk add --no-cache openssl && openssl genrsa -out /export/app.key 2048 && openssl rsa -in /export/app.key -pubout -out /export/app.pub && openssl pkcs8 -topk8 -inform PEM -outform PEM -in /export/app.key -out /export/app.key -nocrypt"
```
> Nota para Linux/AWS: Execute sudo chmod 644 app.key app.pub após a geração para garantir que o Docker tenha permissão de leitura durante o build.

### 4. Subir os Containers
```
docker-compose up -d --build
```
> A API estará disponível em: http://localhost:8080 (ou no IP Público da AWS)

## 📖 Documentação da API (Swagger)

A documentação interativa e o playground de testes podem ser acessados em:
> http://localhost:8080/swagger-ui/index.html (ou no IP Público da AWS)

### Fluxo de Teste Sugerido:

1. Admin Automático: O sistema inicializa um utilizador admin com a senha 12345678 (configurado em AdminUserConfig).
2. Autenticação: Realize um POST em /auth/login para receber o JWT.
3. Autorização: No Swagger, clique em "Authorize", insira o token e teste as rotas protegidas (ex: GET /api/users é restrito a SCOPE_ADMIN).

## ☁️ Deploy na AWS EC2

O projeto foi validado em instâncias Ubuntu na AWS.

1. Security Group: Liberar as portas 22 (SSH) e 8080 (TCP).
2. Ambiente: Instalação do Docker e Docker Compose na instância.
3. Execução: Build e deploy via Docker Compose diretamente na cloud.


