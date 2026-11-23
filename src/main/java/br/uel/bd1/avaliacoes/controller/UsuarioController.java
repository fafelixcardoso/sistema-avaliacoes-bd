package br.uel.bd1.avaliacoes.controller;

import br.uel.bd1.avaliacoes.dao.UsuarioDAO;
import br.uel.bd1.avaliacoes.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() throws SQLException {
        return ResponseEntity.ok(usuarioDAO.listarTodos());
    }

    @GetMapping("/professores")
    public ResponseEntity<List<Usuario>> listarProfessores() throws SQLException {
        return ResponseEntity.ok(usuarioDAO.listarPorTipo("PROFESSOR"));
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<Usuario>> listarAlunos() throws SQLException {
        return ResponseEntity.ok(usuarioDAO.listarPorTipo("ALUNO"));
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O nome do usuário é obrigatório.");
            }
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O e-mail do usuário é obrigatório.");
            }
            if (usuario.getTipoUsuario() == null || 
               (!usuario.getTipoUsuario().equalsIgnoreCase("ALUNO") && !usuario.getTipoUsuario().equalsIgnoreCase("PROFESSOR"))) {
                return ResponseEntity.badRequest().body("O tipo deve ser ALUNO ou PROFESSOR.");
            }
            Usuario salvo = usuarioDAO.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar usuário: " + e.getMessage());
        }
    }
}
