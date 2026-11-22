package br.uel.bd1.avaliacoes.dao;

import br.uel.bd1.avaliacoes.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAO {

    private final DataSource dataSource;

    public UsuarioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Lista todos os usuários cadastrados (para a tabela de usuários).
     */
    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT id_usuario, nome, email, tipo_usuario FROM Usuario ORDER BY nome ASC";
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("tipo_usuario")
                ));
            }
        }
        return lista;
    }

    /**
     * Filtra por tipo ('ALUNO' ou 'PROFESSOR') para carregar os dropdowns corretos.
     */
    public List<Usuario> listarPorTipo(String tipo) throws SQLException {
        String sql = "SELECT id_usuario, nome, email, tipo_usuario FROM Usuario WHERE tipo_usuario = ? ORDER BY nome ASC";
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("tipo_usuario")
                    ));
                }
            }
        }
        return lista;
    }

    /**
     * Cadastra um novo usuário no banco de dados.
     */
    public Usuario salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (nome, email, tipo_usuario) VALUES (?, ?, ?) RETURNING id_usuario";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTipoUsuario());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            }
        }
        return usuario;
    }
}
