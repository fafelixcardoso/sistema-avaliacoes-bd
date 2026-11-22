package br.uel.bd1.avaliacoes.dao;

import br.uel.bd1.avaliacoes.model.Alternativa;
import br.uel.bd1.avaliacoes.model.Questao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestaoDAO {

    private final DataSource dataSource;

    public QuestaoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Lista todas as questões cadastradas (objetivas com suas alternativas e discursivas).
     */
    public List<Questao> listarTodas() throws SQLException {
        String sql = "SELECT id_questao, enunciado, tipo_questao, resposta_esperada FROM Questao ORDER BY id_questao ASC";
        List<Questao> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Questao q = new Questao(
                        rs.getInt("id_questao"),
                        rs.getString("enunciado"),
                        rs.getString("tipo_questao"),
                        rs.getString("resposta_esperada")
                );
                lista.add(q);
            }
        }

        for (Questao q : lista) {
            if ("OBJETIVA".equalsIgnoreCase(q.getTipoQuestao())) {
                q.setAlternativas(buscarAlternativas(q.getIdQuestao()));
            }
        }
        return lista;
    }

    /**
     * Busca as alternativas de uma questão objetiva.
     */
    public List<Alternativa> buscarAlternativas(int idQuestao) throws SQLException {
        String sql = "SELECT id_questao, letra, texto_alternativa, eh_correta FROM Alternativa WHERE id_questao = ? ORDER BY letra ASC";
        List<Alternativa> alts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idQuestao);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alts.add(new Alternativa(
                            rs.getInt("id_questao"),
                            rs.getString("letra"),
                            rs.getString("texto_alternativa"),
                            rs.getBoolean("eh_correta")
                    ));
                }
            }
        }
        return alts;
    }

    /**
     * Cadastra questão objetiva (com alternativas) ou discursiva sob transação JDBC.
     */
    public Questao salvarComTransacao(Questao questao) throws SQLException {
        String sqlQuestao = "INSERT INTO Questao (enunciado, tipo_questao, resposta_esperada) VALUES (?, ?, ?) RETURNING id_questao";
        String sqlAlt = "INSERT INTO Alternativa (id_questao, letra, texto_alternativa, eh_correta) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false); // Início da Transação

            // 1. Grava a Questão
            try (PreparedStatement psQ = conn.prepareStatement(sqlQuestao)) {
                psQ.setString(1, questao.getEnunciado());
                psQ.setString(2, questao.getTipoQuestao());
                psQ.setString(3, questao.getRespostaEsperada());
                try (ResultSet rs = psQ.executeQuery()) {
                    if (rs.next()) {
                        questao.setIdQuestao(rs.getInt(1));
                    }
                }
            }

            // 2. Se for OBJETIVA, grava cada alternativa
            if ("OBJETIVA".equalsIgnoreCase(questao.getTipoQuestao()) && questao.getAlternativa() != null) {
                try (PreparedStatement psA = conn.prepareStatement(sqlAlt)) {
                    for (Alternativa a : questao.getAlternativa()) {
                        psA.setInt(1, questao.getIdQuestao());
                        psA.setString(2, a.getLetra());
                        psA.setString(3, a.getTextoAlternativa());
                        psA.setBoolean(4, a.isEhCorreta());
                        psA.executeUpdate();
                    }
                }
            }

            conn.commit(); // Confirmação
            return questao;
        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
