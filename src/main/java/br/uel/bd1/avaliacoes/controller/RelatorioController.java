package br.uel.bd1.avaliacoes.controller;

import br.uel.bd1.avaliacoes.dao.RelatorioDAO;
import br.uel.bd1.avaliacoes.model.RelatorioDesempenhoDTO;
import br.uel.bd1.avaliacoes.model.RelatorioPendenteDTO;
import br.uel.bd1.avaliacoes.model.RelatorioProporcaoDTO;
import br.uel.bd1.avaliacoes.model.RelatorioQuestaoDificilDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    private final RelatorioDAO relatorioDAO;

    public RelatorioController(RelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

    /**
     * RELATÓRIO 1: Desempenho Geral e Médias por Avaliação
     */
    @GetMapping("/desempenho")
    public ResponseEntity<List<RelatorioDesempenhoDTO>> obterDesempenho() throws SQLException {
        return ResponseEntity.ok(relatorioDAO.obterRelatorioDesempenho());
    }

    /**
     * RELATÓRIO 2: Alunos Pendentes
     */
    @GetMapping("/pendentes")
    public ResponseEntity<List<RelatorioPendenteDTO>> obterPendentes() throws SQLException {
        return ResponseEntity.ok(relatorioDAO.obterRelatorioPendentes());
    }

    /**
     * Gráfico de apoio ao Relatório 2 (Respondidas vs Pendentes)
     */
    @GetMapping("/proporcao")
    public ResponseEntity<List<RelatorioProporcaoDTO>> obterProporcao() throws SQLException {
        return ResponseEntity.ok(relatorioDAO.obterProporcaoRespostas());
    }

    /**
     * RELATÓRIO 3: Ranking de Questões Mais Difíceis / Menor Aproveitamento
     */
    @GetMapping("/questoes-dificeis")
    public ResponseEntity<List<RelatorioQuestaoDificilDTO>> obterQuestoesDificeis() throws SQLException {
        return ResponseEntity.ok(relatorioDAO.obterRelatorioQuestoesDificeis());
    }
}
