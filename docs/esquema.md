# Esquema Relacional

**Integrantes:** Fabrício Felix Cardoso Junior e Guilherme Tanigawa da Silva  
**Disciplina:** Banco de Dados I — UEL  
**Natureza:** Avaliação de Aprendizagem  

---

## 1. Tabelas e Chaves

* **Usuario** (<u>id_usuario</u>, nome, email, tipo_usuario)
  * PK: `id_usuario`
  * UNIQUE: `email`

* **Questao** (<u>id_questao</u>, enunciado, tipo_questao, resposta_esperada)
  * PK: `id_questao`

* **Alternativa** (<u>id_questao</u>, <u>letra</u>, texto_alternativa, eh_correta)
  * PK: `(id_questao, letra)`
  * FK: `id_questao` REFERENCES `Questao(id_questao)`

* **Avaliacao** (<u>id_avaliacao</u>, titulo, descricao, data_inicio, data_fim, id_professor)
  * PK: `id_avaliacao`
  * FK: `id_professor` REFERENCES `Usuario(id_usuario)`

* **AvaliacaoQuestao** (<u>id_avaliacao</u>, <u>id_questao</u>, numero_ordem, valor_questao)
  * PK: `(id_avaliacao, id_questao)`
  * FK: `id_avaliacao` REFERENCES `Avaliacao(id_avaliacao)`
  * FK: `id_questao` REFERENCES `Questao(id_questao)`

* **AvaliacaoAluno** (<u>id_avaliacao</u>, <u>id_aluno</u>, data_realizacao, nota_total)
  * PK: `(id_avaliacao, id_aluno)`
  * FK: `id_avaliacao` REFERENCES `Avaliacao(id_avaliacao)`
  * FK: `id_aluno` REFERENCES `Usuario(id_usuario)`

* **RespostaQuestao** (<u>id_avaliacao</u>, <u>id_aluno</u>, <u>id_questao</u>, texto_resposta, letra_escolhida, nota_obtida)
  * PK: `(id_avaliacao, id_aluno, id_questao)`
  * FK: `(id_avaliacao, id_aluno)` REFERENCES `AvaliacaoAluno(id_avaliacao, id_aluno)`
  * FK: `id_questao` REFERENCES `Questao(id_questao)`

---

## 2. Justificativa da 3ª Forma Normal (3FN)

O esquema relacional está na 3ª Forma Normal (3FN) porque todos os atributos são atômicos (1FN), dependem da chave primária inteira e não de partes dela (2FN), e dependem exclusivamente da chave primária, sem dependências transitivas entre atributos não-chave (3FN).
