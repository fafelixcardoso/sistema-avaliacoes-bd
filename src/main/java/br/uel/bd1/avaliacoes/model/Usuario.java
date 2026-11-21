package br.uel.bd1.avaliacoes.model;

public class Usuario {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String tipoUsuario; // 'PROFESSOR' ou 'ALUNO'

    public Usuario() {}

    public Usuario(Integer idUsuario, String nome, String email, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
