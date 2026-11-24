# Sistema de Avaliações de Aprendizagem

* **Disciplina:** Banco de Dados I (UEL — 2026)  
* **Professor:** Dr. Anderson Paulo Avila Santos  
* **Integrantes:** Fabrício Felix Cardoso Junior e Guilherme Tanigawa da Silva  

---

## 1. Natureza Escolhida e Particularidade

* **Natureza:** Avaliação de aprendizagem (provas, simulados e quizzes).
* **Particularidade:** Cada questão possui uma pontuação configurável por avaliação (`valor_questao`). O sistema realiza a correção automática de questões objetivas comparando a alternativa escolhida com o gabarito (`nota_obtida`) e calcula a nota total do aluno na prova (`nota_total`).

---

## 2. Como Rodar o Sistema

### Pré-requisitos
* Java JDK 17 ou superior
* PostgreSQL em execução (porta 5432)

### Passo 1: Criar e popular o banco de dados
No terminal do PostgreSQL ou pgAdmin, execute:

```bash
psql -U postgres -c "CREATE DATABASE avaliacoes_db;"
psql -U postgres -d avaliacoes_db -f database/schema.sql
psql -U postgres -d avaliacoes_db -f database/carga.sql
```

### Passo 2: Configurar as credenciais (se necessário)
Edite o arquivo `src/main/resources/application.properties` caso a sua senha do PostgreSQL não seja `postgres`:

```properties
spring.datasource.password=sua_senha
```

### Passo 3: Iniciar a aplicação
Na raiz do projeto, execute:

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

### Passo 4: Acessar a interface
Abra no navegador: **`http://localhost:8080`**

---

## 3. Descrição dos Relatórios

1. **Desempenho Geral e Médias por Avaliação (Gráfico de Barras)**
   * Apresenta o número de respostas, nota média, maior e menor nota, e a taxa de adesão de cada avaliação.

2. **Alunos Pendentes por Avaliação (Gráfico de Rosca)**
   * Lista os alunos atribuídos a uma avaliação que ainda não enviaram respostas (`data_realizacao IS NULL`), com o respectivo prazo limite.

3. **Questões Mais Difíceis / Menor Aproveitamento (Gráfico de Barras Horizontais)**
   * Ranking das 10 questões com menor taxa de aproveitamento, comparando a média obtida com a média geral do sistema.
