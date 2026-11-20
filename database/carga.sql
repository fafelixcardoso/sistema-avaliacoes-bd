-- 1. Usuários (2 professores e 20 alunos)
-- -----------------------------------------------------------------------------
INSERT INTO Usuario (id_usuario, nome, email, tipo_usuario) VALUES
(1,  'Prof. Dr. Anderson Paulo Santos', 'anderson.santos@uel.br', 'PROFESSOR'),
(2,  'Profa. Dra. Maria Silveira',       'maria.silveira@uel.br',   'PROFESSOR'),
(3,  'Ana Beatriz Souza',               'ana.souza@uel.br',        'ALUNO'),
(4,  'Bruno Henrique Costa',            'bruno.costa@uel.br',      'ALUNO'),
(5,  'Carla Mendes Dias',               'carla.dias@uel.br',       'ALUNO'),
(6,  'Diego Lucas Rocha',               'diego.rocha@uel.br',      'ALUNO'),
(7,  'Guilherme Tanigawa da Silva',     'guilherme.tanigawa@uel.br', 'ALUNO'),
(8,  'Fabrício Felix Cardoso Junior',   'fabricio.cardoso@uel.br', 'ALUNO'),
(9,  'Gabriela Ramos Lima',             'gabriela.lima@uel.br',    'ALUNO'),
(10, 'Henrique Pires Vieira',           'henrique.vieira@uel.br',  'ALUNO'),
(11, 'Isabela Cristina Duarte',         'isabela.duarte@uel.br',   'ALUNO'),
(12, 'João Vitor Fernandes',            'joao.fernandes@uel.br',   'ALUNO'),
(13, 'Larissa Gomes Ribeiro',           'larissa.ribeiro@uel.br',  'ALUNO'),
(14, 'Lucas Alves Nogueira',            'lucas.nogueira@uel.br',   'ALUNO'),
(15, 'Mariana Borges Teixeira',         'mariana.teixeira@uel.br', 'ALUNO'),
(16, 'Nicolas Gabriel Castro',          'nicolas.castro@uel.br',   'ALUNO'),
(17, 'Otávio Augusto Barros',           'otavio.barros@uel.br',    'ALUNO'),
(18, 'Patrícia Silveira Prado',         'patricia.prado@uel.br',   'ALUNO'),
(19, 'Rafael Correia Melo',             'rafael.melo@uel.br',      'ALUNO'),
(20, 'Sofia Helena Camargo',            'sofia.camargo@uel.br',    'ALUNO'),
(21, 'Thiago Ramos Antunes',            'thiago.antunes@uel.br',   'ALUNO'),
(22, 'Vanessa Toledo Bueno',            'vanessa.bueno@uel.br',    'ALUNO');

-- Sincroniza a sequence de id_usuario
SELECT setval('usuario_id_usuario_seq', (SELECT MAX(id_usuario) FROM Usuario));

-- -----------------------------------------------------------------------------
-- 2. QUESTÕES (30 QUESTÕES: 20 OBJETIVAS E 10 DISCURSIVAS)
-- -----------------------------------------------------------------------------

-- Questões Objetivas (1 a 20)
INSERT INTO Questao (id_questao, enunciado, tipo_questao, resposta_esperada) VALUES
(1,  'No Modelo Entidade-Relacionamento de Peter Chen, qual símbolo gráfico representa um Conjunto de Entidades?', 'OBJETIVA', NULL),
(2,  'Qual construtor gráfico do DER é utilizado para representar relacionamentos?', 'OBJETIVA', NULL),
(3,  'Como um atributo multivalorado é representado graficamente no DER?', 'OBJETIVA', NULL),
(4,  'No mapeamento de um relacionamento 1:N para o modelo relacional, onde deve ficar a chave estrangeira (FK)?', 'OBJETIVA', NULL),
(5,  'No mapeamento de um relacionamento N:M para o modelo relacional, qual estrutura deve ser gerada?', 'OBJETIVA', NULL),
(6,  'Qual forma normal exige que todos os valores das colunas sejam atômicos e indivisíveis?', 'OBJETIVA', NULL),
(7,  'Uma tabela que está na 1FN e não possui dependências funcionais parciais está em qual forma normal?', 'OBJETIVA', NULL),
(8,  'A eliminação de dependências transitivas (um atributo não-chave determinando outro atributo não-chave) garante qual forma normal?', 'OBJETIVA', NULL),
(9,  'Qual comando da SQL DDL é utilizado para criar uma nova tabela no banco de dados?', 'OBJETIVA', NULL),
(10, 'Qual cláusula garante que uma coluna não aceite valores nulos?', 'OBJETIVA', NULL),
(11, 'Qual comando remove uma tabela e todas as suas dependências/chaves estrangeiras no PostgreSQL?', 'OBJETIVA', NULL),
(12, 'O que caracteriza a restrição de Integridade de Entidade no modelo relacional?', 'OBJETIVA', NULL),
(13, 'Em uma especialização exclusiva (marcada com "d" no DER), qual das opções é verdadeira?', 'OBJETIVA', NULL),
(14, 'Quando uma chave estrangeira referencia uma entidade fraca, qual é a composição de sua chave primária?', 'OBJETIVA', NULL),
(15, 'Qual tipo de junção retorna todas as linhas da tabela à esquerda, mesmo quando não há correspondência na tabela à direita?', 'OBJETIVA', NULL),
(16, 'Qual cláusula SQL é utilizada para filtrar registros APÓS um agrupamento com GROUP BY?', 'OBJETIVA', NULL),
(17, 'Qual função de agregação SQL calcula o valor médio de uma coluna numérica?', 'OBJETIVA', NULL),
(18, 'Para limitar o resultado de uma consulta nas 5 primeiras linhas conforme padrão SQL:2008 / PostgreSQL, utiliza-se:', 'OBJETIVA', NULL),
(19, 'O que ocorre quando se utiliza a cláusula ON DELETE CASCADE em uma chave estrangeira?', 'OBJETIVA', NULL),
(20, 'No padrão ANSI/SPARC de 3 níveis de arquitetura de banco de dados, qual nível descreve como os dados são realmente armazenados fisicamente no disco?', 'OBJETIVA', NULL);

-- Questões Discursivas (21 a 30)
INSERT INTO Questao (id_questao, enunciado, tipo_questao, resposta_esperada) VALUES
(21, 'Explique a regra de ouro das dependências para um esquema estar na 3FN, conforme resumido nos slides da disciplina.', 'DISCURSIVA', 'Todo atributo deve depender da chave (1FN), da chave inteira (2FN) e de nada além da chave (3FN).'),
(22, 'Diferencie independência física de dados e independência lógica de dados na arquitetura ANSI/SPARC.', 'DISCURSIVA', 'Independência física é alterar armazenamento e índices sem mexer no esquema lógico; independência lógica é alterar o esquema conceitual sem quebrar as aplicações e visões externas.'),
(23, 'Por que o uso de tipos de ponto flutuante (FLOAT/REAL) é proibido para valores monetários e qual tipo deve ser usado?', 'DISCURSIVA', 'Porque float causa imprecisão por aproximação binária; deve-se usar DECIMAL ou NUMERIC para precisão exata.'),
(24, 'Explique o que é uma entidade fraca e como é formada a sua chave primária no mapeamento relacional.', 'DISCURSIVA', 'Entidade fraca não possui atributos suficientes para formar PK própria; sua PK é composta pela PK da entidade dona mais a sua chave parcial.'),
(25, 'Em quais situações um relacionamento N:M deve ser modelado como Agregação no Modelo Entidade-Relacionamento Estendido?', 'DISCURSIVA', 'Quando o relacionamento precisa participar de outro relacionamento com uma terceira entidade ou relacionamento, tratando a associação como entidade de ordem superior.'),
(26, 'Explique por que uma junção sobre uma coluna que não é chave pode gerar "tuplas espúrias".', 'DISCURSIVA', 'Porque a coluna não identifica linhas unicamente, provocando combinações e produtos cartesianos parciais com dados fictícios que não existiam no domínio original.'),
(27, 'Qual é a diferença fundamental entre as cláusulas WHERE e HAVING em uma consulta SQL?', 'DISCURSIVA', 'WHERE filtra tuplas individuais antes do agrupamento; HAVING filtra os grupos formados após a agregação do GROUP BY.'),
(28, 'Explique como funciona o mapeamento de especialização pela Opção 8A (supertipo e subtipos em tabelas separadas).', 'DISCURSIVA', 'Cria-se uma tabela para o supertipo com atributos comuns e uma tabela para cada subtipo com a mesma chave primária, que também atua como chave estrangeira para o supertipo.'),
(29, 'O que caracteriza a restrição de Integridade Referencial no modelo relacional?', 'DISCURSIVA', 'Uma chave estrangeira deve corresponder exatamente a um valor de chave primária existente na tabela referenciada ou ser inteiramente nula.'),
(30, 'Qual o objetivo de se utilizar transações (BEGIN / COMMIT / ROLLBACK) em operações que modificam múltiplas tabelas?', 'DISCURSIVA', 'Garantir atomicidade e consistência (propriedades ACID), impedindo que modificações parciais fiquem no banco caso ocorra algum erro durante o processo.');

-- Sincroniza a sequence de id_questao
SELECT setval('questao_id_questao_seq', (SELECT MAX(id_questao) FROM Questao));

-- -----------------------------------------------------------------------------
-- 3. ALTERNATIVAS PARA AS QUESTÕES OBJETIVAS (QUESTÕES 1 A 20)
-- -----------------------------------------------------------------------------
INSERT INTO Alternativa (id_questao, letra, texto_alternativa, eh_correta) VALUES
-- Q1
(1, 'A', 'Retângulo', TRUE),
(1, 'B', 'Losango', FALSE),
(1, 'C', 'Elipse', FALSE),
(1, 'D', 'Círculo duplo', FALSE),

-- Q2
(2, 'A', 'Retângulo simples', FALSE),
(2, 'B', 'Losango', TRUE),
(2, 'C', 'Elipse tracejada', FALSE),
(2, 'D', 'Linha pontilhada', FALSE),

-- Q3
(3, 'A', 'Elipse tracejada', FALSE),
(3, 'B', 'Elipse com sub-elipses', FALSE),
(3, 'C', 'Elipse dupla', TRUE),
(3, 'D', 'Retângulo duplo', FALSE),

-- Q4
(4, 'A', 'Sempre no lado 1', FALSE),
(4, 'B', 'Obrigatoriamente em uma nova tabela associativa', FALSE),
(4, 'C', 'Sempre no lado N (lado muitos)', TRUE),
(4, 'D', 'Em ambos os lados simultaneamente', FALSE),

-- Q5
(5, 'A', 'Uma coluna a mais no lado esquerdo', FALSE),
(5, 'B', 'Uma nova relação associativa com as chaves dos dois lados formando a PK composta', TRUE),
(5, 'C', 'Fusão das duas tabelas em uma só', FALSE),
(5, 'D', 'Uma chave estrangeira no lado de participação total apenas', FALSE),

-- Q6
(6, 'A', 'Primeira Forma Normal (1FN)', TRUE),
(6, 'B', 'Segunda Forma Normal (2FN)', FALSE),
(6, 'C', 'Terceira Forma Normal (3FN)', FALSE),
(6, 'D', 'Forma Normal de Boyce-Codd (FNBC)', FALSE),

-- Q7
(7, 'A', '1FN apenas', FALSE),
(7, 'B', 'Segunda Forma Normal (2FN)', TRUE),
(7, 'C', 'Terceira Forma Normal (3FN)', FALSE),
(7, 'D', 'Quarta Forma Normal (4FN)', FALSE),

-- Q8
(8, 'A', '1FN', FALSE),
(8, 'B', '2FN', FALSE),
(8, 'C', 'Terceira Forma Normal (3FN)', TRUE),
(8, 'D', 'FNBC', FALSE),

-- Q9
(9, 'A', 'MAKE TABLE', FALSE),
(9, 'B', 'NEW TABLE', FALSE),
(9, 'C', 'CREATE TABLE', TRUE),
(9, 'D', 'ALTER TABLE', FALSE),

-- Q10
(10, 'A', 'DEFAULT', FALSE),
(10, 'B', 'UNIQUE', FALSE),
(10, 'C', 'NOT NULL', TRUE),
(10, 'D', 'CHECK (VALUE IS TRUE)', FALSE),

-- Q11
(11, 'A', 'DELETE TABLE nome', FALSE),
(11, 'B', 'DROP TABLE nome CASCADE', TRUE),
(11, 'C', 'REMOVE TABLE nome RESTRICT', FALSE),
(11, 'D', 'TRUNCATE TABLE nome FORCE', FALSE),

-- Q12
(12, 'A', 'Nenhum atributo de chave primária pode ser nulo (NULL)', TRUE),
(12, 'B', 'Toda chave estrangeira deve ter valor maior que zero', FALSE),
(12, 'C', 'O banco não pode ter tabelas vazias', FALSE),
(12, 'D', 'Os nomes de colunas devem ser maiúsculos', FALSE),

-- Q13
(13, 'A', 'Uma entidade pode pertencer a vários subtipos ao mesmo tempo', FALSE),
(13, 'B', 'Uma entidade pertence a no máximo um subtipo (subconjuntos disjuntos)', TRUE),
(13, 'C', 'O supertipo é automaticamente removido do banco', FALSE),
(13, 'D', 'Não há chave primária herdada', FALSE),

-- Q14
(14, 'A', 'Apenas o atributo discriminador local', FALSE),
(14, 'B', 'Chave da dona mais a chave parcial da entidade fraca', TRUE),
(14, 'C', 'Um identificador UUID gerado pelo sistema', FALSE),
(14, 'D', 'Apenas a chave da dona', FALSE),

-- Q15
(15, 'A', 'CROSS JOIN', FALSE),
(15, 'B', 'INNER JOIN', FALSE),
(15, 'C', 'LEFT OUTER JOIN (ou LEFT JOIN)', TRUE),
(15, 'D', 'RIGHT JOIN exclusivo', FALSE),

-- Q16
(16, 'A', 'WHERE', FALSE),
(16, 'B', 'HAVING', TRUE),
(16, 'C', 'ORDER BY', FALSE),
(16, 'D', 'GROUP FILTER', FALSE),

-- Q17
(17, 'A', 'SUM()', FALSE),
(17, 'B', 'COUNT()', FALSE),
(17, 'C', 'AVG()', TRUE),
(17, 'D', 'MEDIAN()', FALSE),

-- Q18
(18, 'A', 'FETCH FIRST n ROWS ONLY ou LIMIT n', TRUE),
(18, 'B', 'TOP n ROWS ONLY', FALSE),
(18, 'C', 'MAX ROWS = n', FALSE),
(18, 'D', 'STOP AT n', FALSE),

-- Q19
(19, 'A', 'Impede a exclusão do registro pai', FALSE),
(19, 'B', 'Propaga a remoção do registro pai para os registros filhos dependentes', TRUE),
(19, 'C', 'Converte os campos filhos em valores nulos', FALSE),
(19, 'D', 'Gera um alerta de log sem apagar nada', FALSE),

-- Q20
(20, 'A', 'Nível Externo (Visões)', FALSE),
(20, 'B', 'Nível Conceitual / Lógico', FALSE),
(20, 'C', 'Nível Físico / Interno', TRUE),
(20, 'D', 'Nível de Interface de Rede', FALSE);

-- -----------------------------------------------------------------------------
-- 4. AVALIAÇÕES (5 AVALIAÇÕES CADASTRADAS)
-- -----------------------------------------------------------------------------
INSERT INTO Avaliacao (id_avaliacao, titulo, descricao, data_inicio, data_fim, id_professor) VALUES
(1, 'Prova 1 - Modelagem Conceitual (MER) e Mapeamento', 
    'Primeira prova bimestral abrangendo entidades, atributos, relacionamentos e os 7 passos do mapeamento relacional.', 
    '2026-10-15 08:00:00', '2026-10-15 10:00:00', 1),

(2, 'Simulado 1 - Modelo Relacional e Normalização', 
    'Simulado preparatório para fixação de integridade relacional, DFs e 1FN, 2FN e 3FN.', 
    '2026-10-22 14:00:00', '2026-10-22 18:00:00', 1),

(3, 'Quiz 1 - Fundamentos de SGBD e Arquitetura', 
    'Quiz rápido com foco nos 3 níveis ANSI/SPARC e conceitos fundamentais de independência de dados.', 
    '2026-10-29 19:15:00', '2026-10-29 21:00:00', 2),

(4, 'Prova 2 - SQL DDL e Restrições de Integridade', 
    'Segunda prova bimestral focada na escrita de scripts DDL, integridade de domínio, entidade e referencial.', 
    '2026-11-10 08:00:00', '2026-11-10 10:00:00', 1),

(5, 'Simulado Geral - Banco de Dados I Integrado', 
    'Avaliação abrangente integrando modelagem, normalização e consultas SQL.', 
    '2026-11-20 14:00:00', '2026-11-25 23:59:00', 2);

-- Sincroniza a sequence de id_avaliacao
SELECT setval('avaliacao_id_avaliacao_seq', (SELECT MAX(id_avaliacao) FROM Avaliacao));

-- -----------------------------------------------------------------------------
-- 5. Composição das avaliações (questões e pontuação)
-- -----------------------------------------------------------------------------

-- Avaliação 1 (Prova 1 - Total: 10.00 pts)
INSERT INTO AvaliacaoQuestao (id_avaliacao, id_questao, numero_ordem, valor_questao) VALUES
(1, 1,  1, 1.50),
(1, 2,  2, 1.50),
(1, 3,  3, 1.50),
(1, 4,  4, 1.50),
(1, 5,  5, 1.50),
(1, 24, 6, 2.50);

-- Avaliação 2 (Simulado 1 - Total: 10.00 pts)
INSERT INTO AvaliacaoQuestao (id_avaliacao, id_questao, numero_ordem, valor_questao) VALUES
(2, 6,  1, 1.50),
(2, 7,  2, 1.50),
(2, 8,  3, 1.50),
(2, 12, 4, 1.50), -- Questão 12 reutilizada
(2, 21, 5, 2.00),
(2, 26, 6, 2.00);

-- Avaliação 3 (Quiz 1 - Total: 10.00 pts)
INSERT INTO AvaliacaoQuestao (id_avaliacao, id_questao, numero_ordem, valor_questao) VALUES
(3, 1,  1, 2.00), -- Questão 1 reutilizada
(3, 2,  2, 2.00), -- Questão 2 reutilizada
(3, 20, 3, 3.00),
(3, 22, 4, 3.00);

-- Avaliação 4 (Prova 2 - Total: 10.00 pts)
INSERT INTO AvaliacaoQuestao (id_avaliacao, id_questao, numero_ordem, valor_questao) VALUES
(4, 9,  1, 1.50),
(4, 10, 2, 1.50),
(4, 11, 3, 1.50),
(4, 12, 4, 1.50), -- Questão 12 reutilizada
(4, 19, 5, 1.50),
(4, 23, 6, 2.50);

-- Avaliação 5 (Simulado Geral - Total: 10.00 pts)
INSERT INTO AvaliacaoQuestao (id_avaliacao, id_questao, numero_ordem, valor_questao) VALUES
(5, 4,  1, 1.50), -- Reutilizada
(5, 8,  2, 1.50), -- Reutilizada
(5, 15, 3, 1.50),
(5, 16, 4, 1.50),
(5, 17, 5, 1.50),
(5, 27, 6, 2.50);

-- -----------------------------------------------------------------------------
-- 6. Atribuição e entrega das avaliações
-- -----------------------------------------------------------------------------

-- Avaliação 1: Realizada por 15 alunos (notas consolidadas)
INSERT INTO AvaliacaoAluno (id_avaliacao, id_aluno, data_realizacao, nota_total) VALUES
(1, 3,  '2026-10-15 08:35:00', 9.50),
(1, 4,  '2026-10-15 08:42:00', 7.00),
(1, 5,  '2026-10-15 08:50:00', 8.50),
(1, 6,  '2026-10-15 08:55:00', 6.00),
(1, 7,  '2026-10-15 09:10:00', 8.00),
(1, 8,  '2026-10-15 08:30:00', 10.00),
(1, 9,  '2026-10-15 09:15:00', 7.50),
(1, 10, '2026-10-15 09:20:00', 5.50),
(1, 11, '2026-10-15 08:45:00', 9.00),
(1, 12, '2026-10-15 09:30:00', 7.00),
(1, 13, '2026-10-15 08:58:00', 8.50),
(1, 14, '2026-10-15 09:05:00', 6.50),
(1, 15, '2026-10-15 09:12:00', 9.00),
(1, 16, '2026-10-15 09:25:00', 7.50),
(1, 17, '2026-10-15 09:40:00', 6.00);

-- Avaliação 2 (Simulado 1): Realizada por 14 alunos
INSERT INTO AvaliacaoAluno (id_avaliacao, id_aluno, data_realizacao, nota_total) VALUES
(2, 3,  '2026-10-22 14:30:00', 8.50),
(2, 4,  '2026-10-22 14:45:00', 6.50),
(2, 5,  '2026-10-22 15:10:00', 9.00),
(2, 6,  '2026-10-22 15:30:00', 7.00),
(2, 7,  '2026-10-22 16:00:00', 7.50),
(2, 8,  '2026-10-22 14:20:00', 9.50),
(2, 9,  '2026-10-22 16:15:00', 8.00),
(2, 10, '2026-10-22 16:40:00', 5.00),
(2, 11, '2026-10-22 15:05:00', 8.50),
(2, 12, '2026-10-22 17:10:00', 6.00),
(2, 13, '2026-10-22 15:50:00', 9.00),
(2, 14, '2026-10-22 16:20:00', 6.50),
(2, 18, '2026-10-22 15:40:00', 8.00),
(2, 19, '2026-10-22 16:50:00', 7.50);

-- Avaliação 3 (Quiz 1): Realizado por 12 alunos
INSERT INTO AvaliacaoAluno (id_avaliacao, id_aluno, data_realizacao, nota_total) VALUES
(3, 3,  '2026-10-29 19:30:00', 10.00),
(3, 4,  '2026-10-29 19:40:00', 7.00),
(3, 5,  '2026-10-29 19:45:00', 9.00),
(3, 6,  '2026-10-29 20:00:00', 5.00),
(3, 8,  '2026-10-29 19:25:00', 10.00),
(3, 9,  '2026-10-29 20:10:00', 8.00),
(3, 11, '2026-10-29 19:55:00', 9.00),
(3, 13, '2026-10-29 20:05:00', 7.00),
(3, 15, '2026-10-29 19:50:00', 10.00),
(3, 17, '2026-10-29 20:20:00', 6.00),
(3, 20, '2026-10-29 20:15:00', 8.00),
(3, 21, '2026-10-29 20:30:00', 7.00);

-- Avaliação 4 (Prova 2): Realizadas e pendentes
INSERT INTO AvaliacaoAluno (id_avaliacao, id_aluno, data_realizacao, nota_total) VALUES
(4, 3,  '2026-11-10 08:40:00', 9.00),
(4, 5,  '2026-11-10 08:50:00', 8.50),
(4, 8,  '2026-11-10 08:35:00', 10.00),
(4, 11, '2026-11-10 09:10:00', 8.00),
(4, 13, '2026-11-10 09:20:00', 9.00),
(4, 15, '2026-11-10 09:05:00', 7.50),
(4, 4,  NULL, NULL), -- Pendente
(4, 6,  NULL, NULL), -- Pendente
(4, 7,  NULL, NULL), -- Pendente
(4, 9,  NULL, NULL), -- Pendente
(4, 10, NULL, NULL), -- Pendente
(4, 12, NULL, NULL), -- Pendente
(4, 14, NULL, NULL); -- Pendente

-- Avaliação 5 (Simulado Geral): Em andamento
INSERT INTO AvaliacaoAluno (id_avaliacao, id_aluno, data_realizacao, nota_total) VALUES
(5, 8,  '2026-11-21 15:00:00', 9.50),
(5, 3,  '2026-11-21 16:30:00', 8.50),
(5, 5,  '2026-11-22 10:15:00', 9.00),
(5, 11, '2026-11-22 14:00:00', 8.00),
(5, 15, NULL, NULL), -- Pendente
(5, 16, NULL, NULL), -- Pendente
(5, 17, NULL, NULL), -- Pendente
(5, 18, NULL, NULL), -- Pendente
(5, 19, NULL, NULL), -- Pendente
(5, 20, NULL, NULL), -- Pendente
(5, 21, NULL, NULL), -- Pendente
(5, 22, NULL, NULL); -- Pendente

-- -----------------------------------------------------------------------------
-- 7. Respostas dos alunos por questão
-- -----------------------------------------------------------------------------

-- Respostas da Avaliação 1 (Fabrício - id 8)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(1, 8, 1,  NULL, 'A', 1.50),
(1, 8, 2,  NULL, 'B', 1.50),
(1, 8, 3,  NULL, 'C', 1.50),
(1, 8, 4,  NULL, 'C', 1.50),
(1, 8, 5,  NULL, 'B', 1.50),
(1, 8, 24, 'Entidade fraca não tem atributos que formem PK própria; sua PK é a união da PK da dona com a chave parcial.', NULL, 2.50);

-- Respostas da Avaliação 1 (Bruno - id 4)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(1, 4, 1,  NULL, 'A', 1.50),
(1, 4, 2,  NULL, 'B', 1.50),
(1, 4, 3,  NULL, 'A', 0.00), -- errou (marcou A)
(1, 4, 4,  NULL, 'C', 1.50),
(1, 4, 5,  NULL, 'D', 0.00), -- errou (marcou D)
(1, 4, 24, 'É uma entidade que depende de outra para existir e usa a chave da dona.', NULL, 2.50);

-- Respostas da Avaliação 1 (Ana - id 3)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(1, 3, 1,  NULL, 'A', 1.50),
(1, 3, 2,  NULL, 'B', 1.50),
(1, 3, 3,  NULL, 'C', 1.50),
(1, 3, 4,  NULL, 'C', 1.50),
(1, 3, 5,  NULL, 'B', 1.50),
(1, 3, 24, 'Depende da entidade dona, cuja chave primária é importada como parte da chave.', NULL, 2.00);

-- Respostas da Avaliação 1 (Carla - id 5)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(1, 5, 1,  NULL, 'A', 1.50),
(1, 5, 2,  NULL, 'B', 1.50),
(1, 5, 3,  NULL, 'C', 1.50),
(1, 5, 4,  NULL, 'A', 0.00), -- errou
(1, 5, 5,  NULL, 'B', 1.50),
(1, 5, 24, 'Entidade fraca depende da dona e a chave da dona vira chave estrangeira e primária nela.', NULL, 2.50);

-- Respostas da Avaliação 2 (Fabrício - id 8)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(2, 8, 6,  NULL, 'A', 1.50),
(2, 8, 7,  NULL, 'B', 1.50),
(2, 8, 8,  NULL, 'C', 1.50),
(2, 8, 12, NULL, 'A', 1.50),
(2, 8, 21, 'Todo atributo depende da chave, da chave inteira e de nada além da chave.', NULL, 2.00),
(2, 8, 26, 'Porque colunas não-chave não têm unicidade, misturando tuplas de forma indevida.', NULL, 1.50);

-- Respostas da Avaliação 2 (Henrique - id 10)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(2, 10, 6,  NULL, 'A', 1.50),
(2, 10, 7,  NULL, 'A', 0.00), -- errou
(2, 10, 8,  NULL, 'B', 0.00), -- errou
(2, 10, 12, NULL, 'A', 1.50),
(2, 10, 21, 'Elimina redundâncias e tabelas repetidas.', NULL, 1.00),
(2, 10, 26, 'Porque a junção duplica os dados sem chave.', NULL, 1.00);

-- Respostas da Avaliação 4 (Fabrício - id 8)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(4, 8, 9,  NULL, 'C', 1.50),
(4, 8, 10, NULL, 'C', 1.50),
(4, 8, 11, NULL, 'B', 1.50),
(4, 8, 12, NULL, 'A', 1.50),
(4, 8, 19, NULL, 'B', 1.50),
(4, 8, 23, 'Tipos float sofrem imprecisão binária de arredondamento; valores contábeis exigem DECIMAL.', NULL, 2.50);

-- Respostas da Avaliação 4 (Ana - id 3)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(4, 3, 9,  NULL, 'C', 1.50),
(4, 3, 10, NULL, 'C', 1.50),
(4, 3, 11, NULL, 'B', 1.50),
(4, 3, 12, NULL, 'A', 1.50),
(4, 3, 19, NULL, 'A', 0.00), -- errou
(4, 3, 23, 'Moedas e dinheiro exigem DECIMAL para garantir exatidão nos centavos sem erros de float.', NULL, 2.50);

-- Respostas da Avaliação 5 (Fabrício - id 8)
INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) VALUES
(5, 8, 4,  NULL, 'C', 1.50),
(5, 8, 8,  NULL, 'C', 1.50),
(5, 8, 15, NULL, 'C', 1.50),
(5, 8, 16, NULL, 'B', 1.50),
(5, 8, 17, NULL, 'C', 1.50),
(5, 8, 27, 'WHERE filtra tuplas antes do agrupamento; HAVING filtra grupos após agregação.', NULL, 2.00);
