-- Remove tabelas se já existirem
DROP TABLE IF EXISTS RespostaQuestao CASCADE;
DROP TABLE IF EXISTS AvaliacaoAluno CASCADE;
DROP TABLE IF EXISTS AvaliacaoQuestao CASCADE;
DROP TABLE IF EXISTS Alternativa CASCADE;
DROP TABLE IF EXISTS Questao CASCADE;
DROP TABLE IF EXISTS Avaliacao CASCADE;
DROP TABLE IF EXISTS Usuario CASCADE;
DROP DOMAIN IF EXISTS NOTA_VALOR CASCADE;

-- Domínio para notas e pontuação (0.00 a 100.00)
CREATE DOMAIN NOTA_VALOR AS DECIMAL(5, 2)
  CHECK (VALUE >= 0.00 AND VALUE <= 100.00);

-- 1. Usuários (professores e alunos)
CREATE TABLE Usuario (
  id_usuario   SERIAL       PRIMARY KEY,
  nome         VARCHAR(100) NOT NULL,
  email        VARCHAR(100) NOT NULL UNIQUE,
  tipo_usuario VARCHAR(20)  NOT NULL CHECK (tipo_usuario IN ('PROFESSOR', 'ALUNO'))
);

-- 2. Questões (banco de questões)
CREATE TABLE Questao (
  id_questao        SERIAL       PRIMARY KEY,
  enunciado         TEXT         NOT NULL,
  tipo_questao      VARCHAR(20)  NOT NULL CHECK (tipo_questao IN ('OBJETIVA', 'DISCURSIVA')),
  resposta_esperada TEXT
);

-- 3. Alternativas (opções das questões objetivas)
CREATE TABLE Alternativa (
  id_questao        INTEGER     NOT NULL,
  letra             CHAR(1)     NOT NULL CHECK (letra IN ('A', 'B', 'C', 'D', 'E')),
  texto_alternativa TEXT        NOT NULL,
  eh_correta        BOOLEAN     NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id_questao, letra),
  FOREIGN KEY (id_questao) REFERENCES Questao(id_questao)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- 4. Avaliações (provas, simulados e quizzes)
CREATE TABLE Avaliacao (
  id_avaliacao SERIAL       PRIMARY KEY,
  titulo       VARCHAR(100) NOT NULL,
  descricao    TEXT,
  data_inicio  TIMESTAMP    NOT NULL,
  data_fim     TIMESTAMP    NOT NULL,
  id_professor INTEGER      NOT NULL,
  FOREIGN KEY (id_professor) REFERENCES Usuario(id_usuario)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CHECK (data_fim > data_inicio)
);

-- 5. Composição da avaliação (questões na prova)
CREATE TABLE AvaliacaoQuestao (
  id_avaliacao  INTEGER    NOT NULL,
  id_questao    INTEGER    NOT NULL,
  numero_ordem  INTEGER    NOT NULL CHECK (numero_ordem > 0),
  valor_questao NOTA_VALOR NOT NULL CHECK (valor_questao > 0.00),
  PRIMARY KEY (id_avaliacao, id_questao),
  UNIQUE (id_avaliacao, numero_ordem),
  FOREIGN KEY (id_avaliacao) REFERENCES Avaliacao(id_avaliacao)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (id_questao) REFERENCES Questao(id_questao)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 6. Atribuição e entrega da prova pelo aluno
CREATE TABLE AvaliacaoAluno (
  id_avaliacao    INTEGER    NOT NULL,
  id_aluno        INTEGER    NOT NULL,
  data_realizacao TIMESTAMP  DEFAULT NULL,
  nota_total      NOTA_VALOR DEFAULT NULL,
  PRIMARY KEY (id_avaliacao, id_aluno),
  FOREIGN KEY (id_avaliacao) REFERENCES Avaliacao(id_avaliacao)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (id_aluno) REFERENCES Usuario(id_usuario)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 7. Respostas dos alunos por questão
CREATE TABLE RespostaQuestao (
  id_avaliacao    INTEGER    NOT NULL,
  id_aluno        INTEGER    NOT NULL,
  id_questao      INTEGER    NOT NULL,
  texto_resposta  TEXT,
  letra_escolhida CHAR(1),
  nota_obtida     NOTA_VALOR NOT NULL DEFAULT 0.00,
  PRIMARY KEY (id_avaliacao, id_aluno, id_questao),
  FOREIGN KEY (id_avaliacao, id_aluno) REFERENCES AvaliacaoAluno(id_avaliacao, id_aluno)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (id_questao) REFERENCES Questao(id_questao)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Índices
CREATE INDEX idx_usuario_email ON Usuario(email);
CREATE INDEX idx_avaliacao_datas ON Avaliacao(data_inicio, data_fim);
CREATE INDEX idx_avaliacaoaluno_aluno ON AvaliacaoAluno(id_aluno);
CREATE INDEX idx_resposta_questao ON RespostaQuestao(id_questao);
