package br.uel.bd1.avaliacoes.controller;

import br.uel.bd1.avaliacoes.dao.QuestaoDAO;
import br.uel.bd1.avaliacoes.model.Questao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/questoes")
@CrossOrigin(origins = "*")
public class QuestaoController {

    private final QuestaoDAO questaoDAO;

    public QuestaoController(QuestaoDAO questaoDAO) {
        this.questaoDAO = questaoDAO;
    }

    @GetMapping
    public ResponseEntity<List<Questao>> listarTodas() throws SQLException {
        return ResponseEntity.ok(questaoDAO.listarTodas());
    }

    @PostMapping
    public ResponseEntity<?> criarQuestao(@RequestBody Questao questao) {
        try {
            if (questao.getEnunciado() == null || questao.getEnunciado().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O enunciado da questão é obrigatório.");
            }
            if (questao.getTipoQuestao() == null || 
               (!questao.getTipoQuestao().equalsIgnoreCase("OBJETIVA") && !questao.getTipoQuestao().equalsIgnoreCase("DISCURSIVA"))) {
                return ResponseEntity.badRequest().body("O tipo deve ser OBJETIVA ou DISCURSIVA.");
            }
            Questao salva = questaoDAO.salvarComTransacao(questao);
            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar questão: " + e.getMessage());
        }
    }
}
