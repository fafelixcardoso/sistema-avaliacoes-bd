package br.uel.bd1.avaliacoes.controller;

import br.uel.bd1.avaliacoes.dao.RespostaDAO;
import br.uel.bd1.avaliacoes.model.SubmissaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/respostas")
@CrossOrigin(origins = "*")
public class RespostaController {

    private final RespostaDAO respostaDAO;

    public RespostaController(RespostaDAO respostaDAO) {
        this.respostaDAO = respostaDAO;
    }

    @PostMapping
    public ResponseEntity<?> submeterRespostas(@RequestBody SubmissaoDTO submissao) {
        try {
            if (submissao.getIdAvaliacao() == null || submissao.getIdAluno() == null) {
                return ResponseEntity.badRequest().body("Identificadores de avaliação e aluno são obrigatórios.");
            }
            if (submissao.getRespostas() == null || submissao.getRespostas().isEmpty()) {
                return ResponseEntity.badRequest().body("Nenhuma resposta foi enviada.");
            }

            BigDecimal notaFinal = respostaDAO.submeterAvaliacao(submissao);
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Avaliação finalizada com sucesso!");
            resposta.put("idAvaliacao", submissao.getIdAvaliacao());
            resposta.put("idAluno", submissao.getIdAluno());
            resposta.put("notaFinal", notaFinal);

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar submissão: " + e.getMessage());
        }
    }
}
