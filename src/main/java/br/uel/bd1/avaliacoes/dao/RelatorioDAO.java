package br.uel.bd1.avaliacoes.dao;

import br.uel.bd1.avaliacoes.model.RelatorioDesempenhoDTO;
import br.uel.bd1.avaliacoes.model.RelatorioPendenteDTO;
import br.uel.bd1.avaliacoes.model.RelatorioProporcaoDTO;
import br.uel.bd1.avaliacoes.model.RelatorioQuestaoDificilDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas consultas dos relatórios analíticos
 * conforme as queries definidas em database/relatorios.sql.
 */
@Repository
public class RelatorioDAO {

    private final DataSource dataSource;

    public RelatorioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Relatório 1: Desempenho geral e médias por avaliação.
     */
    public List<RelatorioDesempenhoDTO> obterRelatorioDesempenho() throws SQLException {
        String sql = 
            "SELECT " +
            "    a.id_avaliacao, " +
            "    a.titulo AS avaliacao, " +
            "    prof.nome AS professor_responsavel, " +
            "    COUNT(aa.data_realizacao) AS total_respostas, " +
            "    ROUND(AVG(aa.nota_total), 2) AS media_nota, " +
            "    MAX(aa.nota_total) AS maior_nota, " +
            "    MIN(aa.nota_total) AS menor_nota, " +
            "    ROUND( " +
            "        COUNT(aa.data_realizacao) * 100.0 / " +
            "        (SELECT COUNT(*) FROM AvaliacaoAluno sub WHERE sub.id_avaliacao = a.id_avaliacao), " +
            "        1 " +
            "    ) AS taxa_adesao_pct " +
            "FROM Avaliacao a " +
            "INNER JOIN Usuario prof ON a.id_professor = prof.id_usuario " +
            "INNER JOIN AvaliacaoAluno aa ON a.id_avaliacao = aa.id_avaliacao " +
            "WHERE aa.data_realizacao IS NOT NULL " +
            "  AND a.data_inicio >= '2026-01-01' " +
            "GROUP BY a.id_avaliacao, a.titulo, prof.nome " +
            "HAVING COUNT(aa.data_realizacao) >= 1 " +
            "ORDER BY media_nota DESC";

        List<RelatorioDesempenhoDTO> resultado = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioDesempenhoDTO dto = new RelatorioDesempenhoDTO();
                dto.setIdAvaliacao(rs.getInt("id_avaliacao"));
                dto.setAvaliacao(rs.getString("avaliacao"));
                dto.setProfessorResponsavel(rs.getString("professor_responsavel"));
                dto.setTotalRespostas(rs.getLong("total_respostas"));
                dto.setMediaNota(rs.getBigDecimal("media_nota"));
                dto.setMaiorNota(rs.getBigDecimal("maior_nota"));
                dto.setMenorNota(rs.getBigDecimal("menor_nota"));
                dto.setTaxaAdesaoPct(rs.getBigDecimal("taxa_adesao_pct"));
                resultado.add(dto);
            }
        }
        return resultado;
    }

    /**
     * Relatório 2: Alunos pendentes por avaliação.
     */
    public List<RelatorioPendenteDTO> obterRelatorioPendentes() throws SQLException {
        String sql = 
            "SELECT " +
            "    a.id_avaliacao, " +
            "    a.titulo AS avaliacao, " +
            "    a.data_fim AS prazo_limite, " +
            "    u.id_usuario AS id_aluno, " +
            "    u.nome AS nome_aluno, " +
            "    u.email AS email_aluno, " +
            "    'PENDENTE' AS status_resposta " +
            "FROM Avaliacao a " +
            "INNER JOIN AvaliacaoAluno aa ON a.id_avaliacao = aa.id_avaliacao " +
            "INNER JOIN Usuario u ON aa.id_aluno = u.id_usuario " +
            "LEFT JOIN RespostaQuestao rq ON aa.id_avaliacao = rq.id_avaliacao AND aa.id_aluno = rq.id_aluno " +
            "WHERE aa.data_realizacao IS NULL " +
            "  AND rq.id_questao IS NULL " +
            "  AND (a.titulo LIKE '%Prova%' OR a.titulo LIKE '%Simulado%') " +
            "  AND a.data_fim >= '2026-10-01 00:00:00' " +
            "ORDER BY a.data_fim ASC, u.nome ASC";

        List<RelatorioPendenteDTO> resultado = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioPendenteDTO dto = new RelatorioPendenteDTO();
                dto.setIdAvaliacao(rs.getInt("id_avaliacao"));
                dto.setAvaliacao(rs.getString("avaliacao"));
                Timestamp ts = rs.getTimestamp("prazo_limite");
                dto.setPrazoLimite(ts != null ? ts.toLocalDateTime() : null);
                dto.setIdAluno(rs.getInt("id_aluno"));
                dto.setNomeAluno(rs.getString("nome_aluno"));
                dto.setEmailAluno(rs.getString("email_aluno"));
                dto.setStatusResposta(rs.getString("status_resposta"));
                resultado.add(dto);
            }
        }
        return resultado;
    }

    /**
     * Consulta agregada de suporte ao gráfico do Relatório 2 (Respondidas vs Pendentes).
     */
    public List<RelatorioProporcaoDTO> obterProporcaoRespostas() throws SQLException {
        String sql = 
            "SELECT " +
            "    a.titulo AS avaliacao, " +
            "    COUNT(CASE WHEN aa.data_realizacao IS NOT NULL THEN 1 END) AS total_respondidas, " +
            "    COUNT(CASE WHEN aa.data_realizacao IS NULL THEN 1 END) AS total_pendentes " +
            "FROM Avaliacao a " +
            "INNER JOIN AvaliacaoAluno aa ON a.id_avaliacao = aa.id_avaliacao " +
            "GROUP BY a.id_avaliacao, a.titulo " +
            "ORDER BY a.id_avaliacao ASC";

        List<RelatorioProporcaoDTO> resultado = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.add(new RelatorioProporcaoDTO(
                    rs.getString("avaliacao"),
                    rs.getLong("total_respondidas"),
                    rs.getLong("total_pendentes")
                ));
            }
        }
        return resultado;
    }

    /**
     * Relatório 3: Ranking de questões com mais erros.
     */
    public List<RelatorioQuestaoDificilDTO> obterRelatorioQuestoesDificeis() throws SQLException {
        String sql = 
            "SELECT " +
            "    q.id_questao, " +
            "    q.tipo_questao, " +
            "    SUBSTRING(q.enunciado FROM 1 FOR 65) || '...' AS enunciado_resumido, " +
            "    COUNT(rq.id_aluno) AS total_respostas_avaliadas, " +
            "    ROUND(AVG(rq.nota_obtida), 2) AS media_obtida, " +
            "    ROUND(AVG(aq.valor_questao), 2) AS valor_referencia, " +
            "    ROUND((AVG(rq.nota_obtida) / NULLIF(AVG(aq.valor_questao), 0)) * 100.0, 1) AS taxa_aproveitamento_pct, " +
            "    (SELECT ROUND(AVG(nota_obtida), 2) FROM RespostaQuestao) AS media_global_sistema, " +
            "    ROUND( " +
            "        AVG(rq.nota_obtida) - (SELECT AVG(nota_obtida) FROM RespostaQuestao), " +
            "        2 " +
            "    ) AS diferenca_vs_media_global " +
            "FROM Questao q " +
            "INNER JOIN AvaliacaoQuestao aq ON q.id_questao = aq.id_questao " +
            "INNER JOIN RespostaQuestao rq ON aq.id_avaliacao = rq.id_avaliacao AND aq.id_questao = rq.id_questao " +
            "WHERE q.tipo_questao IN ('OBJETIVA', 'DISCURSIVA') " +
            "GROUP BY q.id_questao, q.tipo_questao, q.enunciado " +
            "ORDER BY taxa_aproveitamento_pct ASC, media_obtida ASC " +
            "FETCH FIRST 10 ROWS ONLY";

        List<RelatorioQuestaoDificilDTO> resultado = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioQuestaoDificilDTO dto = new RelatorioQuestaoDificilDTO();
                dto.setIdQuestao(rs.getInt("id_questao"));
                dto.setTipoQuestao(rs.getString("tipo_questao"));
                dto.setEnunciadoResumido(rs.getString("enunciado_resumido"));
                dto.setTotalRespostasAvaliadas(rs.getLong("total_respostas_avaliadas"));
                dto.setMediaObtida(rs.getBigDecimal("media_obtida"));
                dto.setValorReferencia(rs.getBigDecimal("valor_referencia"));
                dto.setTaxaAproveitamentoPct(rs.getBigDecimal("taxa_aproveitamento_pct"));
                dto.setMediaGlobalSistema(rs.getBigDecimal("media_global_sistema"));
                dto.setDiferencaVsMediaGlobal(rs.getBigDecimal("diferenca_vs_media_global"));
                resultado.add(dto);
            }
        }
        return resultado;
    }
}
