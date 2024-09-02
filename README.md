# 💻 Gestão API

Esta aplicação é uma API REST desenvolvida em Java 17 utilizando o framework Spring Boot para gerenciamento de veículos e clientes. A API permite operações CRUD (Create, Read, Update, Delete) para ambos os recursos e inclui autenticação e autorização usando JSON Web Tokens (JWT).

API REST para gerenciar informações de veículos e clientes

As tecnologias utilizadas são:

- Java 17
- JPA
- Maven
- Spring Boot
- MySQL
- Docker
- Swagger

## 🔌 Configurações para inicialização

**Atenção**: Você precisa ter o Docker instalado na sua máquina.

Para configurar a inicialização da API usando Docker, você pode seguir os seguintes passos:

1. Clone o repositório do projeto:

```bash
git clone https://github.com/sheilapaiva/gestao-api.git
```

2. Acesse a pasta do projeto:

```bash
cd /gestao-api/api
```

3. Execute o Docker Compose para construir e iniciar os contêineres (utilizando o arquivo docker-compose.yml):

```bash
docker-compose up --build -d
```

O parâmetro --build garante que as imagens dos contêineres sejam construídas caso não existam, e o parâmetro -d executa os contêineres em segundo plano.

Ou

```bash
docker-compose up -d
```

4. Para parar os contêineres, execute:

```bash
docker-compose down
```

Isso irá parar e remover os contêineres criados.

## 🧾 Documentação

A documentação da api foi gerada através do Swagger e pode ser acessada em `http://localhost:8080/api/swagger-ui/index.html`

## 📌 Endpoints

Após iniciar a aplicação, você pode acessar a API nos seguintes endpoints:

### Documentação

- `/swagger-ui.html`

### Autenticação

- [POST] /admins/login - Realizar login e obter um JWT

A rota /admins/login permite que os administradores realizem o login na aplicação. Para isso, deve enviar um JSON com o username e password. O sistema então verifica se as credenciais são válidas e, se forem, retorna um token de autenticação.

**Exemplo de dados para logar um administrador (JSON)**

```json
{
  "username": "admin",
  "password": "admin"
}
```

Ao fazer login com sucesso, a API retorna um token JWT que deve ser incluído em todas as solicitações subsequentes para autenticar o usuário no sistema. Esse token deve ser enviado no cabeçalho de autenticação da seguinte maneira:

```
Authorization: Bearer <token>
```

**Exemplo de dados para autenticar**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjYXNldGVjbmljbyIsInN1YiI6InN0cmluZyIsImV4cCI6MTcxMTU3NDQ3MH0.FrUxU5jx2N7SSo24Ug_0ysn1qmB_2BIetfR5rLJ-3M4"
}
```

Estas abaixo só estão **liberadas** para acesso **com autenticação**. Caso queira acessar estas rotas é necessário **realizar autenticação** no sistema.

### Veículos

- [POST] /vehicles - Criar um novo veículo

**Exemplo de dados para criar um Veículo (JSON)**

```json
{
  "plate": "BHG1C23", //ou "ABC-1234"
  "brand": "Volvo",
  "model": "Volvo FH",
  "color": "branco",
  "year": "2023"
}
```

**Restrições:**
- Precisa ser uma placa em um formato válido (antigo ou mercosul).
- Precisa ser um ano válido (1900 até o ano atual).
- Pode apenas ter um veículo com a mesma placa.
- Precisa preencher todos os campos.

- [GET] /vehicles/{id} - Obter um veículo por ID

### Exibe as informações de um veículo específico

**Restrições:**
- Precisa ser um id válido.

- [GET] /vehicles - Listar todos os veículos

### Exibe as informações de todos os veículos

- [PUT] /vehicles/{id} - Atualizar um veículo

**Exemplo de dados para atualizar um veículo (JSON)**

```json
{
  "plate": "BHG1C23", //ou "ABC-1234"
  "brand": "Volvo",
  "model": "Volvo FM",
  "color": "azul",
  "year": "2023"
}
```

**Restrições:**
- Precisa ser uma placa em um formato válido (antigo ou mercosul).
- Precisa ser um ano válido (1900 até o ano atual).
- Pode apenas ter um veículo com a mesma placa.

- [DELETE] /vehicles/{id} - Deletar um veículo

### Deleta as informações de um veículo específico

**Restrições:**
- Precisa ser um id válido.

### Clientes

- [POST] /clients - Criar um novo cliente

**Exemplo de dados para criar um Cliente (JSON)**

```json
{
  "name": "Maria Silva",
  "email": "maria.silva@example.com",
  "phone": "11987654321",
  "cnpj": "12.345.678/0001-95",
  "address": "Rua Exemplo, 123, Bairro Centro, Cidade Exemplo, Estado Exemplo"
}
```

**Restrições:**
- Precisa ser um endereço de e-mail em um formato válido.
- Precisa ser um cnpj em um formato válido.
- Pode apenas ter um usuário com o mesmo email/cnpj.
- Precisa preencher todos os campos.

- [GET] /clients/{id} - Obter um cliente por ID

### Exibe as informações de um cliente específico

**Restrições:**
- Precisa ser um id válido.

- [GET] /clients - Listar todos os clientes

### Exibe as informações de todos os clientes

- [PUT] /clients/{id} - Atualizar um cliente

**Exemplo de dados para atualizar um Cliente (JSON)**

```json
{
  "id": 1,
  "name": "Maria Silva",
  "email": "maria.silva.exemplo@example.com",
  "phone": "11987654321",
  "cnpj": "12.345.678/0001-95",
  "address": "Rua Exemplo, 123, Bairro Exemplo Centro, Cidade Exemplo, Estado Exemplo"
}
```

**Restrições:**
- Precisa ser um id válido.
- Precisa ser um endereço de e-mail em um formato válido.
- Precisa ser um cnpj em um formato válido.
- Pode apenas ter um usuário com o mesmo email/cnpj.

- [DELETE] /clients/{id} - Deletar um cliente

### Deleta as informações de um cliente específico

**Restrições:**
- Precisa ser um id válido.


## DDLs

Os DDLs para criação das tabelas no banco de dados MySQL:

```sql
CREATE TABLE table_vehicle (
    id bigint NOT NULL AUTO_INCREMENT,
    brand varchar(255) NOT NULL,
    color varchar(255) NOT NULL,
    model varchar(255) NOT NULL,
    plate varchar(255) NOT NULL,
    year varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (plate)
);

CREATE TABLE table_clients (
  id bigint NOT NULL AUTO_INCREMENT,
  address varchar(255) NOT NULL,
  cnpj varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  phone varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (cnpj),
  UNIQUE KEY (email)
);

CREATE TABLE table_admin (
  id bigint NOT NULL AUTO_INCREMENT,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (username)
);
```
Se quiser verificar as tabelas criadas pelo Docker, você pode fazer o seguinte: 

 1. Acessar o Container MySQL:
 
```bash
 docker exec -it mysql mysql -u myuser -p
```

Digite a senha quando solicitado.

2. Verificar as Tabelas Criadas:

```sql
USE mydatabase;
SHOW TABLES;
```

Para verificar a estrutura de uma tabela específica:

```sql
DESCRIBE table_vehicle;
```

Com esses passos é possível verificar os DDLs das tabelas criadas a partir das suas classes de entidade.

## Localização dos Logs

Os logs da aplicação são armazenados em:

- **Desenvolvimento Local**: `./logs`
- **Docker**: `./logs:/app/logs`
