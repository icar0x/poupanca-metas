# Sistema de Poupança com Metas

Aplicação backend (CLI) para criação e acompanhamento de metas financeiras, com cálculo automático de juros sobre o saldo poupado.

## Identificação

- **Aluno:** Ícaro
- **Disciplina:** Banco de Dados
- **Professor:** _Anderson Costa_

## Sobre o projeto

O sistema permite que um usuário crie metas financeiras (ex: viagem, notebook, reserva de emergência), registre depósitos nessas metas e acompanhe o progresso em direção ao valor alvo. O sistema também simula o rendimento de juros sobre o saldo acumulado, como uma poupança real.

O objetivo é demonstrar, na prática, a evolução de um CRUD simples para uma aplicação com maior integração entre sistema e banco de dados, utilizando **View**, **Function** e **Procedure** no PostgreSQL de forma integrada às funcionalidades reais do sistema.

### Principais funcionalidades
- Cadastro de usuários
- Criação de metas financeiras com valor alvo e data limite
- Registro de depósitos, com conclusão automática da meta ao atingir o valor alvo
- Listagem de metas com progresso (%), dias restantes e status
- Cálculo e aplicação de juros sobre o saldo de uma meta
- Relatório financeiro mensal consolidado

## Tecnologias utilizadas

- Java 17+
- Spring Boot 3.x (Spring Data JPA)
- PostgreSQL
- Maven
- Lombok

## Banco de dados

- **SGBD:** PostgreSQL

### Principais tabelas
- `usuario` — dados do usuário
- `meta` — metas financeiras criadas pelo usuário
- `deposito` — histórico de depósitos realizados em cada meta
- `historico_juros` — histórico de juros aplicados a cada meta

### View criada
**`vw_metas_com_progresso`** — consolida cada meta com seu percentual de progresso, dias restantes até a data alvo e status atual (`em_progresso`, `concluida` ou `atrasada`). Utilizada nas funcionalidades de listagem de metas e consulta de progresso.

### Function criada
**`calcular_juros_poupanca(meta_id, taxa_anual)`** — calcula os juros compostos acumulados sobre o saldo atual de uma meta, com base no tempo decorrido desde sua criação. Utilizada na funcionalidade de cálculo de juros, que soma o resultado ao saldo da meta.

### Procedure criada
**`registrar_deposito_em_meta(meta_id, valor_deposito)`** — registra um depósito em uma meta, validando que o valor é positivo e que a meta ainda não está concluída, atualiza o saldo da meta e a marca como concluída automaticamente caso o valor alvo seja atingido. Utilizada na funcionalidade de depósito.

## Como executar

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL instalado e em execução

### Passo a passo

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   cd poupanca-metas
   ```

2. Crie o banco de dados:
   ```sql
   CREATE DATABASE poupanca_db;
   ```

3. Execute os scripts SQL **na ordem**, conectado ao banco `poupanca_db` (via `psql` ou pgAdmin):
   ```
   database/tables/01_criar_tabelas.sql
   database/views/02_criar_view_metas.sql
   database/functions/03_criar_function_juros.sql
   database/procedures/04_criar_procedure_deposito.sql
   database/inserts/05_dados_teste.sql
   ```

   Via terminal:
   ```bash
   psql -U postgres -d poupanca_db -f database/tables/01_criar_tabelas.sql
   psql -U postgres -d poupanca_db -f database/views/02_criar_view_metas.sql
   psql -U postgres -d poupanca_db -f database/functions/03_criar_function_juros.sql
   psql -U postgres -d poupanca_db -f database/procedures/04_criar_procedure_deposito.sql
   psql -U postgres -d poupanca_db -f database/inserts/05_dados_teste.sql
   ```

4. Confirme a configuração em `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/poupanca_db
   spring.datasource.username=postgres
   spring.datasource.password=<sua senha do postgres>
   ```

5. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

6. O menu interativo aparecerá no terminal. Use o usuário de teste (id `1`) para explorar as funcionalidades com os dados pré-cadastrados.