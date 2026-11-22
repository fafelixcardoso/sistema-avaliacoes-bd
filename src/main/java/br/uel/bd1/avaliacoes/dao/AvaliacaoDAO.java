package br.uel.bd1.avaliacoes.dao;

import br.uel.bd1.avaliacoes.model.Avaliacao;
import br.uel.bd1.avaliacoes.model.AvaliacaoQuestao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AvaliacaoDAO {

    private final DataSource dataSource;

    public AvaliacaoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Lista todas as avaliações cadastradas.
     */
    public List<Avaliacao> listarTodas() throws SQLException {
        String sql = "SELECT a.id_avaliacao, a.titulo, a.descricao, a.data_inicio, a.data_fim, a.id_professor, u.nome AS nome_prof " +
                     "FROM Avaliacao a " +
                     "INNER JOIN Usuario u ON a.id_professor = u.id_usuario " +
                     "ORDER BY a.data_inicio DESC";
        List<Avaliacao> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Avaliacao av = new Avaliacao(
                        rs.getInt("id_avaliacao"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_inicio").toLocalDateTime(),
                        rs.getTimestamp("data_fim").toLocalDateTime(),
                        rs.getInt("id_professor")
                );
                av.setNomeProfessor(rs.getString("nome_prof"));
                lista.add(av);
            }
        }
        return lista;
    }

    /**
     * Busca as questões associadas a uma avaliação com seus respectivos valores em pontos.
     * Carrega as alternativas se a questão for objetiva.
     */
    public List<AvaliacaoQuestao> buscarQuestoesDaAvaliacao(int idAvaliacao) throws SQLException {
        String sql = "SELECT aq.id_avaliacao, aq.id_questao, aq.numero_ordem, aq.valor_questao, " +
                     "       q.enunciado, q.tipo_questao " +
                     "FROM AvaliacaoQuestao aq " +
                     "INNER JOIN Questao q ON aq.id_questao = q.id_questao " +
                     "WHERE aq.id_avaliacao = ? " +
                     "ORDER BY aq.numero_ordem ASC";
        List<AvaliacaoQuestao> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAvaliacao);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AvaliacaoQuestao aq = new AvaliacaoQuestao(
                            rs.getInt("id_avaliacao"),
                            rs.getInt("id_questao"),
                            rs.getInt("numero_ordem"),
                            rs.getBigDecimal("valor_questao")
                    );
                    aq.setEnunciado(rs.getString("enunciado"));
                    aq.setTipoQuestao(rs.getString("tipo_questao"));
                    lista.add(aq);
                }
            }
        }

        String sqlAlt = "SELECT id_questao, letra, texto_alternativa, eh_correta FROM Alternativa WHERE id_questao = ? ORDER BY letra ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement psAlt = conn.prepareStatement(sqlAlt)) {
            for (AvaliacaoQuestao aq : lista) {
                if ("OBJETIVA".equalsIgnoreCase(aq.getTipoQuestao())) {
                    psAlt.setInt(1, aq.getIdQuestao());
                    try (ResultSet rsA = psAlt.executeQuery()) {
                        List<br.uel.bd1.avaliacoes.model.Alternativa> alts = new ArrayList<>();
                        while (rsA.next()) {
                            alts.add(new br.uel.bd1.avaliacoes.model.Alternativa(
                                    rsA.getInt("id_questao"),
                                    rsA.getString("letra"),
                                    rsA.getString("texto_alternativa"),
                                    rsA.getBoolean("eh_correta")
                            ));
                        }
                        aq.setAlternativas(alts);
                    }
                }
            }
        }
        return lista;
    }

    /**
     * Salva a avaliação, associa as questões com suas pontuações individuais
     * e vincula os alunos atribuídos sob transação JDBC.
     */
    public Avaliacao salvarComQuestoesEAlunos(Avaliacao avaliacao, List<AvaliacaoQuestao> questoes, List<Integer> alunosIds) throws SQLException {
        String sqlAvaliacao = "INSERT INTO Avaliacao (titulo, descricao, data_inicio, data_fim, id_professor) " +
                              "VALUES (?, ?, ?, ?, ?) RETURNING id_avaliacao";
        String sqlItemQuestao = "INSERT INTO AvaliacaoQuestao (id_avaliacao, id_questao, numero_ordem, valor_questao) " +
                                "VALUES (?, ?, ?, ?)";
        String sqlItemAluno = "INSERT INTO AvaliacaoAluno (id_avaliacao, id_aluno) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false); // Inicia Transação

            // 1. Grava Avaliação
            try (PreparedStatement psAv = conn.prepareStatement(sqlAvaliacao)) {
                psAv.setString(1, avaliacao.getTitulo());
                psAv.setString(2, avaliacao.getDescricao());
                psAv.setTimestamp(3, Timestamp.valueOf(avaliacao.getDataInicio()));
                psAv.setTimestamp(4, Timestamp.valueOf(avaliacao.getDataFim()));
                psAv.setInt(5, avaliacao.getIdProfessor());
                try (ResultSet rs = psAv.executeQuery()) {
                    if (rs.next()) {
                        avaliacao.setIdAvaliacao(rs.getInt(1));
                    }
                }
            }

            // 2. Grava as Questões da Avaliação (com valor_questao de cada uma)
            if (questoes != null && !questoes.isEmpty()) {
                try (PreparedStatement psQ = conn.prepareStatement(sqlItemQuestao)) {
                    int ordem = 1;
                    for (AvaliacaoQuestao aq : questoes) {
                        psQ.setInt(1, avaliacao.getIdAvaliacao());
                        psQ.setInt(2, aq.getIdQuestao());
                        psQ.setInt(3, aq.getNumeroOrdem() != null ? aq.getNumeroOrdem() : ordem++);
                        psQ.setBigDecimal(4, aq.getValorQuestao());
                        psQ.executeUpdate();
                    }
                }
            }

            // 3. Atribui Alunos para a Avaliação
            if (alunosIds != null && !alunosIds.isEmpty()) {
                try (PreparedStatement psAl = conn.prepareStatement(sqlItemAluno)) {
                    for (Integer alunoId : alunosIds) {
                        psAl.setInt(1, avaliacao.getIdAvaliacao());
                        psAl.setInt(2, alunoId);
                        psAl.executeUpdate();
                    }
                }
            }

            conn.commit(); // Confirma Transação
            return avaliacao;
        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Rollback em caso de falha
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
