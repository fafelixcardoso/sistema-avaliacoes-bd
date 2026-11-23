package br.uel.bd1.avaliacoes.model;

import java.time.LocalDateTime;

public class RelatorioPendenteDTO {
    private Integer idAvaliacao;
    private String avaliacao;
    private LocalDateTime prazoLimite;
    private Integer idAluno;
    private String nomeAluno;
    private String emailAluno;
    private String statusResposta;

    public RelatorioPendenteDTO() {}

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDateTime getPrazoLimite() {
        return prazoLimite;
    }

    public void setPrazoLimite(LocalDateTime prazoLimite) {
        this.prazoLimite = prazoLimite;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getStatusResposta() {
        return statusResposta;
    }

    public void setStatusResposta(String statusResposta) {
        this.statusResposta = statusResposta;
    }
}
