
# Projeto de Autenticação

Este é um projeto de autenticação em Java utilizando **Spring Boot**, **Maven**, **Docker** e **PostgreSQL**. O sistema utiliza **JWT** para autenticação, **BCrypt** para criptografia de senha e conta com funcionalidades de cadastro de usuário com confirmação de email, login, e recuperação de senha.

## Tecnologias Usadas

- **Java 21**
- **Spring Boot 3.4.4**
- **Maven** para gerenciamento de dependências
- **Docker** para contêinerização
- **PostgreSQL** como banco de dados
- **Spring Security** para autenticação e autorização
- **JWT** para autenticação baseada em token
- **BCrypt** para criptografia de senha
- **ModelMapper** para mapeamento de objetos
- **SpringDoc OpenAPI** para documentação de API

## Requisitos

- **Java 21** ou superior instalado
- **Docker** instalado

## Como Rodar o Projeto

### 1. Clone o repositório

Primeiro, clone o repositório para a sua máquina local:

```bash
git clone https://github.com/Iagoolima/atividade_fabiano
cd atividade_fabiano
```

### 2. Rodando a aplicação com Docker Compose

No diretório raiz do projeto, onde está o arquivo `docker-compose.yml`, execute o seguinte comando para construir e subir os containers:

```bash
docker-compose up --build
```

- **`--build`**: O comando vai construir a imagem da aplicação antes de rodar os containers.
- Isso irá subir o Spring Boot em um container e o PostgreSQL em outro.

### 3. Acessando a Aplicação

Após a aplicação estar em funcionamento, você pode acessá-la no seguinte endereço:

- **Aplicação Web**: [http://localhost:8080](http://localhost:8080)
- **Banco de Dados PostgreSQL**: Porta `5432`.

### 4. Parando os Containers

Caso precise parar os containers, execute:

```bash
docker-compose down
```

Isso irá parar e remover os containers, mas manterá as imagens e volumes persistidos.

## Funcionalidades da Aplicação

### Cadastro de Usuário

- Realiza o cadastro de novos usuários com confirmação de email.
- O usuário é registrado no sistema.

### Login

- Utiliza **JWT** para autenticação.
- Ao realizar o login, um token JWT é retornado, e este token deve ser utilizado nas requisições subsequentes para autenticação.

### Recuperação de Senha

- O usuário pode solicitar a recuperação de senha. Um **token de recuperação** é enviado por email, permitindo que o usuário atualize a senha.

## Estrutura de Segurança

A aplicação utiliza **Spring Security** para proteger as rotas e garantir a autenticidade dos usuários:

- **Login e Cadastro** são feitos com validações específicas.
- **JWT** é utilizado para autenticação em cada requisição.
- O sistema possui permissões para os papéis de **ADMIN** e **USER**.

### BCrypt para Criptografia de Senha

- A senha do usuário é armazenada de forma segura, utilizando o algoritmo **BCrypt**.
- Durante o cadastro, a senha é criptografada antes de ser salva no banco de dados.

## Banco de Dados

O banco de dados utilizado é o **PostgreSQL**, configurado no `docker-compose.yml`. Quando a aplicação subir, ele criará o banco e as tabelas necessárias automaticamente.

## APIs

A aplicação expõe algumas rotas para autenticação e operações de usuário. Para uma lista completa de rotas e documentação da API, consulte a documentação gerada pelo **SpringDoc OpenAPI**:

- Acesse a documentação da API em [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui).

## Diagrama

![alt text](diagrama.png)
