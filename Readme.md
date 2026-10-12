# TraceBank API

API REST para gestão de contas bancárias e processamento de transações financeiras (depósitos, saques e transferências), com consistência de saldo garantida por transações atômicas e rastreabilidade completa de cada movimentação através de extrato.

Cada cliente se cadastra, autentica e opera **apenas as próprias contas**.

## O problema que resolve

Sistemas financeiros precisam de uma base confiável para manter saldos e histórico. Os riscos clássicos são saldo negativo indevido, transferências em que o dinheiro sai de uma conta e nunca chega na outra, e acesso a contas de terceiros. O TraceBank trata cada um explicitamente:

| Risco | Como é tratado |
|---|---|
| Saque acima do saldo | Validação de regra de negócio antes de qualquer alteração; resposta `422` |
| Dinheiro "perdido" numa transferência | Débito e crédito na **mesma transação** (`@Transactional`): ou os dois acontecem, ou nenhum |
| Histórico não confiável | Toda movimentação gera um registro permanente; o extrato é a fonte da verdade |
| Erros de arredondamento | Valores monetários em `BigDecimal` / `NUMERIC(15,2)`, nunca `double` |
| Acesso a contas de terceiros | Autorização por dono do recurso em todo endpoint (proteção contra IDOR) |
| Vazamento de credenciais | Senhas com hash BCrypt; segredos fora do repositório |

## Stack

- **Java 21** e **Spring Boot**
- **Spring Data JPA / Hibernate** e **PostgreSQL**
- **Flyway** para versionamento do schema (o Hibernate apenas valida, nunca altera o banco)
- **Spring Security** com **JWT** (stateless, HS256)
- **Bean Validation**, **OpenAPI/Swagger** (springdoc) e **Actuator**
- **JUnit 5**, **Mockito** e **Testcontainers** (Postgres real nos testes de integração)

## Arquitetura

Organização **por feature** (cada domínio é autocontido), com camadas dentro de cada uma:

```
com.tracebank
 ├── auth/          registro, login, emissão e leitura do JWT
 ├── customer/      dados cadastrais do cliente
 ├── account/       contas e verificação de propriedade
 ├── transaction/   depósitos, saques, transferências e extrato
 └── shared/        tratamento global de erros, configuração de segurança e OpenAPI
```

Fluxo de uma requisição:

```
Controller  →  Service  →  Repository  →  PostgreSQL
(HTTP, DTO,    (regra de    (Spring Data
 validação)    negócio,      JPA)
               @Transactional)
```

- **DTOs** isolam o contrato da API do modelo de persistência (a entidade nunca é exposta).
- **Exceções de domínio** são traduzidas para HTTP num único `@RestControllerAdvice`.
- **Documentação e autorização** vivem em meta-anotações (`@ApiDeposit`, `@IsAccountOwner`), mantendo os controllers enxutos.

## Endpoints

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/auth/register` | Público | Cadastra cliente e usuário |
| `POST` | `/auth/login` | Público | Autentica e devolve o JWT |
| `GET` | `/customers/{customerId}` | Próprio cliente | Consulta o cadastro |
| `POST` | `/customers/{customerId}/accounts` | Próprio cliente | Abre uma conta |
| `GET` | `/accounts/{accountId}` | Dono da conta | Consulta a conta e o saldo |
| `POST` | `/accounts/{accountId}/deposits` | Dono da conta | Deposita |
| `POST` | `/accounts/{accountId}/withdrawals` | Dono da conta | Saca (valida saldo) |
| `POST` | `/accounts/{accountId}/transfers` | Dono da conta de origem | Transfere para outra conta (atômico) |
| `GET` | `/accounts/{accountId}/statement` | Dono da conta | Extrato, do mais recente ao mais antigo |
| `GET` | `/actuator/health` | Público | Verificação de saúde da aplicação |

Documentação interativa em `http://localhost:8080/swagger-ui.html` (com botão **Authorize** para o token).

### Códigos de resposta

| Código | Quando |
|---|---|
| `400` | Dados inválidos ou autotransferência |
| `401` | Token ausente, inválido ou expirado; credenciais incorretas |
| `403` | Recurso de outro cliente (também para conta inexistente, para não revelar quais ids existem) |
| `404` | Destino de transferência inexistente |
| `409` | E-mail ou CPF já cadastrado |
| `422` | Regra de negócio violada (saldo insuficiente, conta inativa) |

## Executando localmente

### Pré-requisitos

- Java 21
- PostgreSQL em execução
- Docker em execução (apenas para os testes de integração)

### 1. Banco de dados

```sql
CREATE DATABASE tracebank;
CREATE USER tracebank_user WITH ENCRYPTED PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE tracebank TO tracebank_user;
\c tracebank
GRANT ALL ON SCHEMA public TO tracebank_user;
```

### 2. Variáveis de ambiente

Copie o modelo e preencha os valores:

```bash
cp .env.example .env
```

| Variável | Descrição |
|---|---|
| `DB_NAME` | Nome do banco |
| `DB_USER` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Segredo de assinatura do JWT, **mínimo 32 caracteres** |
| `JWT_EXPIRATION` | Validade do token (ex.: `1h`, `30m`) |

Para gerar um segredo forte:

```bash
openssl rand -base64 48
```

O arquivo `.env` é lido automaticamente na inicialização e **nunca é versionado**.

### 3. Subindo a aplicação

```bash
./mvnw spring-boot:run
```

As migrations do Flyway são aplicadas automaticamente. Para conferir:

```bash
curl http://localhost:8080/actuator/health
```

### Exemplo de uso

```bash
# 1. Cadastro
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" \
  -d '{"name":"Maria Silva","cpf":"12345678901","birthDate":"1990-05-20","email":"maria@example.com","password":"senhaSegura123"}'

# 2. Login (guarde o accessToken)
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" \
  -d '{"email":"maria@example.com","password":"senhaSegura123"}'

# 3. Abrir conta (use o customerId devolvido no cadastro)
curl -X POST http://localhost:8080/customers/1/accounts -H "Authorization: Bearer <accessToken>" \
  -H "Content-Type: application/json" -d '{"type":"CHECKING"}'

# 4. Depositar
curl -X POST http://localhost:8080/accounts/1/deposits -H "Authorization: Bearer <accessToken>" \
  -H "Content-Type: application/json" -d '{"amount":500.00}'
```

## Testes

```bash
./mvnw test                          # tudo (unitários + integração)
./mvnw test "-Dtest=*ServiceTest"    # só unitários (rápido, sem Docker)
./mvnw test "-Dtest=*IntegrationTest" # só integração (precisa do Docker)
```

- **Unitários** (JUnit 5 + Mockito): cobrem as regras de negócio dos services, incluindo os limites (saque do saldo exato, saque de um centavo a mais) e os caminhos de erro.
- **Integração** (Spring + Testcontainers): sobem a aplicação inteira contra um PostgreSQL real e descartável, exercitando migrations, mapeamento JPA, segurança e a API de ponta a ponta. Cobrem a atomicidade das transferências e a isolação entre clientes.
- Os testes não dependem do `.env` nem do banco local.

## Decisões de design

- **Dirty checking em vez de `save()` redundante:** o saldo é alterado numa entidade gerenciada dentro da transação, e o Hibernate persiste a mudança no commit.
- **`400` vs `422`:** `400` para requisição malformada; `422` para requisição válida que viola uma regra de negócio no estado atual (ex.: saldo insuficiente).
- **Respostas de autenticação iguais** para e-mail inexistente e senha errada, e mitigação de *timing attack* no login (o Spring executa a comparação BCrypt também quando o usuário não existe), para dificultar a enumeração de usuários.
- **JWT de curta duração** e nunca com dados sensíveis no payload (ele é assinado, não criptografado).
- **`GlobalExceptionHandler` centralizado:** nenhum `try/catch` espalhado pelos controllers.
