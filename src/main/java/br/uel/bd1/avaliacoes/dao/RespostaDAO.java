package br.uel.bd1.avaliacoes.dao;

import br.uel.bd1.avaliacoes.model.SubmissaoDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO para submissão e correção de avaliações.
 */
@Repository
public class RespostaDAO {

    private final DataSource dataSource;

    public RespostaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Submete as respostas de uma avaliação e atualiza a nota total do aluno.
     */
    public BigDecimal submeterAvaliacao(SubmissaoDTO submissao) throws SQLException {
        String sqlBuscaQuestao = 
            "SELECT q.tipo_questao, aq.valor_questao " +
            "FROM AvaliacaoQuestao aq " +
            "INNER JOIN Questao q ON aq.id_questao = q.id_questao " +
            "WHERE aq.id_avaliacao = ? AND aq.id_questao = ?";

        String sqlBuscaCorreta = 
            "SELECT letra FROM Alternativa WHERE id_questao = ? AND eh_correta = TRUE";

        String sqlInsertResposta = 
            "INSERT INTO RespostaQuestao (id_avaliacao, id_aluno, id_questao, texto_resposta, letra_escolhida, nota_obtida) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlUpdateAvaliacaoAluno = 
            "UPDATE AvaliacaoAluno " +
            "SET data_realizacao = CURRENT_TIMESTAMP, nota_total = ? " +
            "WHERE id_avaliacao = ? AND id_aluno = ?";

        Connection conn = null;
        BigDecimal notaTotalAcumulada = BigDecimal.ZERO;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false); // INÍCIO DA TRANSAÇÃO

            // 1. Processar cada resposta do aluno e totalizar nota
            for (SubmissaoDTO.ItemRespostaDTO item : submissao.getRespostas()) {
                String tipoQuestao = "OBJETIVA";
                BigDecimal valorQuestao = BigDecimal.ONE;

                try (PreparedStatement stmtQ = conn.prepareStatement(sqlBuscaQuestao)) {
                    stmtQ.setInt(1, submissao.getIdAvaliacao());
                    stmtQ.setInt(2, item.getIdQuestao());
                    try (ResultSet rsQ = stmtQ.executeQuery()) {
                        if (rsQ.next()) {
                            tipoQuestao = rsQ.getString("tipo_questao");
                            valorQuestao = rsQ.getBigDecimal("valor_questao");
                        }
                    }
                }

                BigDecimal notaObtida = BigDecimal.ZERO;

                if ("OBJETIVA".equalsIgnoreCase(tipoQuestao)) {
                    String letraCorreta = null;
                    try (PreparedStatement stmtC = conn.prepareStatement(sqlBuscaCorreta)) {
                        stmtC.setInt(1, item.getIdQuestao());
                        try (ResultSet rsC = stmtC.executeQuery()) {
                            if (rsC.next()) {
                                letraCorreta = rsC.getString("letra");
                            }
                        }
                    }

                    if (letraCorreta != null && letraCorreta.equalsIgnoreCase(item.getLetraEscolhida())) {
                        notaObtida = valorQuestao;
                    }
                } else {
                    // Questão discursiva preenchida
                    if (item.getTextoResposta() != null && !item.getTextoResposta().trim().isEmpty()) {
                        notaObtida = valorQuestao;
                    }
                }

                notaTotalAcumulada = notaTotalAcumulada.add(notaObtida);

                // Gravar resposta individual
                try (PreparedStatement stmtR = conn.prepareStatement(sqlInsertResposta)) {
                    stmtR.setInt(1, submissao.getIdAvaliacao());
                    stmtR.setInt(2, submissao.getIdAluno());
                    stmtR.setInt(3, item.getIdQuestao());
                    stmtR.setString(4, item.getTextoResposta());
                    stmtR.setString(5, item.getLetraEscolhida());
                    stmtR.setBigDecimal(6, notaObtida);
                    stmtR.executeUpdate();
                }
            }

            notaTotalAcumulada = notaTotalAcumulada.setScale(2, RoundingMode.HALF_UP);

            // 2. Atualizar registro em AvaliacaoAluno com timestamp e nota calculada
            try (PreparedStatement stmtU = conn.prepareStatement(sqlUpdateAvaliacaoAluno)) {
                stmtU.setBigDecimal(1, notaTotalAcumulada);
                stmtU.setInt(2, submissao.getIdAvaliacao());
                stmtU.setInt(3, submissao.getIdAluno());
                int rows = stmtU.executeUpdate();
                if (rows == 0) {
                    String sqlInsertAA = 
                        "INSERT INTO AvaliacaoAluno (id_avaliacao, id_aluno, data_realizacao, nota_total) " +
                        "VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
                    try (PreparedStatement stmtIns = conn.prepareStatement(sqlInsertAA)) {
                        stmtIns.setInt(1, submissao.getIdAvaliacao());
                        stmtIns.setInt(2, submissao.getIdAluno());
                        stmtIns.setBigDecimal(3, notaTotalAcumulada);
                        stmtIns.executeUpdate();
                    }
                }
            }

            conn.commit(); // CONFIRMAÇÃO DA TRANSAÇÃO
            return notaTotalAcumulada;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // DESFAZ EM CASO DE ERRO
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
