[README.md](https://github.com/user-attachments/files/31156196/README.md)
# Spring AI DIO Project

Projeto desenvolvido como parte do **Bootcamp Santander 2026**, com base no módulo de **Spring AI**.

O objetivo do projeto é colocar em prática conceitos de desenvolvimento de APIs REST com Spring Boot, persistência de dados com MySQL e integração com Inteligência Artificial utilizando Spring AI e os modelos da OpenAI.

---

## Sobre o projeto

A aplicação funciona como uma API para gerenciamento de **transações financeiras**.

É possível cadastrar e consultar transações através de endpoints REST e também utilizar Inteligência Artificial para interpretar comandos enviados por áudio.

No fluxo com IA, a aplicação:

1. recebe um arquivo de áudio;
2. converte o áudio em texto;
3. envia o texto para o modelo de IA;
4. permite que a IA identifique qual operação deve ser realizada;
5. executa o caso de uso correspondente, como cadastrar ou consultar transações;
6. gera uma resposta;
7. converte a resposta novamente para áudio.

A aplicação mantém a separação entre **domínio, casos de uso e infraestrutura**, seguindo a arquitetura apresentada no projeto-base da DIO.

---

## Funcionalidades

A API possui funcionalidades para:

- Cadastrar transações financeiras;
- Consultar transações por categoria;
- Validar os dados enviados para criação de uma transação;
- Transcrever arquivos de áudio utilizando IA;
- Utilizar um modelo de linguagem através do Spring AI;
- Permitir que a IA utilize os casos de uso da aplicação através de Tool Calling;
- Converter respostas em texto para áudio;
- Realizar o fluxo completo de interação financeira através de áudio.

---

##  Tecnologias utilizadas

O projeto utiliza:

- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring AI**
- **OpenAI API**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Bean Validation / Jakarta Validation**
- **Lombok**
- **Maven**

---

#  Melhorias implementadas

Além da implementação baseada no projeto original da DIO, foram realizadas melhorias na aplicação.

## 1. Validação de dados com Bean Validation

Foi adicionada a dependência do **Spring Boot Validation** e utilizadas anotações do Jakarta Bean Validation para impedir que dados inválidos sejam cadastrados.

O objeto utilizado para criação de uma transação possui validações como:

```java
public record TransactionRequest(
        @NotBlank String description,
        @NotNull Category category,
        @Positive long amount
) {
}
```

As regras implementadas garantem que:

- `description` não seja nula ou vazia;
- `category` seja obrigatória;
- `amount` seja um valor positivo.

O controller utiliza `@Valid` para executar automaticamente essas validações antes de processar a requisição.

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public TransactionResponse createTransaction(
        @Valid @RequestBody TransactionRequest request
) {
    ...
}
```

Dessa forma, requisições inválidas são rejeitadas antes de chegarem à regra de negócio.

---

## 2. Melhoria da documentação da API

A documentação do projeto também foi aprimorada através deste README, contendo:

- objetivo da aplicação;
- tecnologias utilizadas;
- requisitos para execução;
- configuração do banco de dados;
- configuração da OpenAI API;
- principais endpoints;
- exemplos de requisições;
- instruções para testar o fluxo principal;
- descrição das melhorias realizadas.

---

#  Pré-requisitos

Antes de executar o projeto é necessário possuir:

- **Java 17** ou superior compatível;
- **MySQL Server**;
- **Maven** ou utilizar o Maven Wrapper incluído no projeto;
- uma **OpenAI API Key** com créditos disponíveis;
- Postman, Insomnia ou outra ferramenta semelhante para testar a API.

---

#  Configurando o MySQL

A aplicação utiliza um banco MySQL chamado:

```text
transaction
```

Crie o banco através do MySQL Workbench ou terminal:

```sql
CREATE DATABASE transaction;
```

O arquivo:

```text
src/main/resources/application.properties
```

possui a configuração de conexão com o banco:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/transaction
spring.datasource.username=root
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Caso o usuário ou senha do seu MySQL sejam diferentes, altere:

```properties
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

Com:

```properties
spring.jpa.hibernate.ddl-auto=update
```

o Hibernate cria ou atualiza automaticamente as tabelas necessárias após a aplicação iniciar.

---

#  Configurando a OpenAI API

A chave da OpenAI é obtida através da variável de ambiente:

```properties
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

Portanto, é necessário criar a variável `OPENAI_API_KEY` antes de executar a aplicação.

### Windows PowerShell

```powershell
$env:OPENAI_API_KEY="sua-chave-aqui"
```

### Windows CMD

```cmd
set OPENAI_API_KEY=sua-chave-aqui
```

### Linux / macOS

```bash
export OPENAI_API_KEY="sua-chave-aqui"
```

---

# ▶ Como executar a aplicação

Primeiro clone o repositório e acesse a pasta do projeto.

Em seguida, certifique-se de que:

1. o MySQL está executando;
2. o banco `transaction` foi criado;
3. usuário e senha do banco estão corretos;
4. a variável `OPENAI_API_KEY` está configurada.

### Windows

Na pasta do projeto execute:

```cmd
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Também é possível executar diretamente através de uma IDE, como o IntelliJ IDEA, iniciando a classe:

```text
Application.java
```

Por padrão, a aplicação ficará disponível em:

```text
http://localhost:8080
```

---

#  Documentação da API

## Transações

### Criar uma transação

```http
POST /transactions
```

Exemplo de body:

```json
{
  "description": "Compra no supermercado",
  "category": "GROCERIES",
  "amount": 150
}
```

Exemplo utilizando `curl`:

```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Compra no supermercado",
    "category": "GROCERIES",
    "amount": 150
  }'
```

Quando a transação é criada corretamente, a API retorna o status:

```text
201 Created
```

---

### Categorias disponíveis

Atualmente são aceitas as seguintes categorias:

```text
GROCERIES
PHARMA
AUTO
```

---

### Consultar transações por categoria

```http
GET /transactions/{category}
```

Exemplo:

```http
GET /transactions/GROCERIES
```

Utilizando `curl`:

```bash
curl http://localhost:8080/transactions/GROCERIES
```

A API retorna as transações pertencentes à categoria informada.

---

#  Fluxo com Inteligência Artificial

O endpoint principal de integração com IA é:

```http
POST /transactions/ai
```

Tipo da requisição:

```text
multipart/form-data
```

O parâmetro esperado é:

```text
file
```

Este arquivo deve conter um áudio com um comando relacionado às transações financeiras.

Exemplos de frases:

```text
Gastei 50 reais no supermercado.
```

ou:

```text
Quais foram meus gastos com mercado?
```

O fluxo executado pela aplicação é:

```text
Áudio
  ↓
Transcrição
  ↓
Spring AI / OpenAI
  ↓
Identificação da operação
  ↓
Caso de uso da aplicação
  ↓
MySQL
  ↓
Resposta da IA
  ↓
Conversão para áudio
  ↓
Arquivo MP3
```

A resposta do endpoint é um arquivo:

```text
audio/mp3
```

---

#  Outros endpoints de IA

O projeto também contém endpoints auxiliares que podem ser utilizados para testar individualmente recursos do Spring AI.

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/chat` | Envia um prompt utilizando `ChatClient` |
| `GET` | `/api/chat-model` | Envia um prompt diretamente para o modelo |
| `POST` | `/api/transcribe` | Transcreve um arquivo de áudio |
| `POST` | `/api/sinthesize` | Converte um texto para áudio |
| `POST` | `/transactions/ai` | Executa o fluxo completo da aplicação |

---

#  Como testar o fluxo principal

Uma forma simples de verificar o funcionamento da aplicação é realizar o seguinte fluxo.

## 1. Criar uma transação

No Postman, crie uma requisição:

```http
POST http://localhost:8080/transactions
```

Selecione:

```text
Body → raw → JSON
```

Envie:

```json
{
  "description": "Compra no mercado",
  "category": "GROCERIES",
  "amount": 200
}
```

A API deverá responder com:

```text
201 Created
```

e retornar os dados da transação criada.

---

## 2. Consultar a transação

Faça:

```http
GET http://localhost:8080/transactions/GROCERIES
```

A transação cadastrada anteriormente deverá aparecer na resposta.

---

## 3. Testar a validação

Também é possível verificar a melhoria implementada com Bean Validation.

Envie uma transação inválida:

```json
{
  "description": "",
  "category": null,
  "amount": -100
}
```

A requisição deverá ser rejeitada porque:

```text
description → não pode estar vazia
category    → não pode ser nula
amount      → deve ser positivo
```

Isso demonstra que os dados são validados antes da execução da regra de negócio.

---

## 4. Testar o fluxo com IA

No Postman crie:

```http
POST http://localhost:8080/transactions/ai
```

Selecione:

```text
Body → form-data
```

Adicione:

```text
KEY: file
TYPE: File
VALUE: selecione um arquivo de áudio
```

O áudio pode conter uma frase como:

```text
Gastei 80 reais no mercado.
```

A aplicação realizará a transcrição do áudio, enviará o comando para a IA e permitirá que o modelo utilize os casos de uso registrados na aplicação.

Ao final, a resposta é convertida novamente para áudio e retornada como um arquivo MP3.

> Para utilizar esse fluxo é necessário possuir uma OpenAI API Key válida e com créditos disponíveis.

---

#  Executando os testes

Os testes automatizados podem ser executados através do Maven.

### Windows

```cmd
mvnw.cmd test
```

### Linux / macOS

```bash
./mvnw test
```

---

#  Estrutura do projeto

A aplicação está organizada principalmente nas seguintes camadas:

```text
src/main/java/com/br
│
├── application
│   ├── input
│   ├── output
│   ├── ListTransactionByCategoryUseCase
│   └── PersistTransactionUseCase
│
├── domain
│   ├── Category
│   ├── Transaction
│   ├── TransactionId
│   └── TransactionRepository
│
├── infrastructure
│   ├── http
│   │   ├── request
│   │   ├── response
│   │   └── TransactionController
│   │
│   └── persistence
│
├── ChatClientController
├── ChatModelController
├── TextToSpeechController
├── TranscriptionController
└── Application
```

Essa organização separa as regras de negócio das implementações relacionadas a HTTP, persistência e serviços externos.

---

---

## 👨‍💻 Autor

Projeto desenvolvido por **Daniel Borges** como parte dos estudos realizados no **Bootcamp Santander 2026 / DIO**.
