package br.uel.bd1.avaliacoes.controller;

import br.uel.bd1.avaliacoes.dao.AvaliacaoDAO;
import br.uel.bd1.avaliacoes.model.Avaliacao;
import br.uel.bd1.avaliacoes.model.AvaliacaoQuestao;
import br.uel.bd1.avaliacoes.model.NovaAvaliacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    private final AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoController(AvaliacaoDAO avaliacaoDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
    }

    @GetMapping
    public ResponseEntity<List<Avaliacao>> listarTodas() throws SQLException {
        return ResponseEntity.ok(avaliacaoDAO.listarTodas());
    }

    @GetMapping("/{id}/questoes")
    public ResponseEntity<List<AvaliacaoQuestao>> buscarQuestoes(@PathVariable int id) throws SQLException {
        return ResponseEntity.ok(avaliacaoDAO.buscarQuestoesDaAvaliacao(id));
    }

    @PostMapping
    public ResponseEntity<?> criarAvaliacao(@RequestBody NovaAvaliacaoDTO dto) {
        try {
            if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O título da avaliação é obrigatório.");
            }
            if (dto.getDataInicio() == null || dto.getDataFim() == null) {
                return ResponseEntity.badRequest().body("As datas de início e término são obrigatórias.");
            }
            if (dto.getDataFim().isBefore(dto.getDataInicio())) {
                return ResponseEntity.badRequest().body("A data de término não pode ser anterior à data de início.");
            }
            if (dto.getIdProfessor() == null) {
                return ResponseEntity.badRequest().body("O professor responsável deve ser informado.");
            }

            Avaliacao av = new Avaliacao();
            av.setTitulo(dto.getTitulo());
            av.setDescricao(dto.getDescricao());
            av.setDataInicio(dto.getDataInicio());
            av.setDataFim(dto.getDataFim());
            av.setIdProfessor(dto.getIdProfessor());

            List<AvaliacaoQuestao> questoes = new ArrayList<>();
            if (dto.getQuestoes() != null) {
                int ordem = 1;
                for (NovaAvaliacaoDTO.ItemQuestaoDTO item : dto.getQuestoes()) {
                    AvaliacaoQuestao aq = new AvaliacaoQuestao();
                    aq.setIdQuestao(item.getIdQuestao());
                    aq.setValorQuestao(item.getValorQuestao());
                    aq.setNumeroOrdem(item.getNumeroOrdem() != null ? item.getNumeroOrdem() : ordem++);
                    questoes.add(aq);
                }
            }

            Avaliacao criada = avaliacaoDAO.salvarComQuestoesEAlunos(av, questoes, dto.getAlunosIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar avaliação: " + e.getMessage());
        }
    }
}
