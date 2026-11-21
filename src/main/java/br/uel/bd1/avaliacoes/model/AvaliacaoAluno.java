package br.uel.bd1.avaliacoes.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AvaliacaoAluno {
    private Integer idAvaliacao;
    private Integer idAluno;
    private LocalDateTime dataRealizacao;
    private BigDecimal notaTotal;

    // Campos informativos para interface
    private String nomeAluno;
    private String emailAluno;
    private String tituloAvaliacao;

    public AvaliacaoAluno() {}

    public AvaliacaoAluno(Integer idAvaliacao, Integer idAluno, LocalDateTime dataRealizacao, BigDecimal notaTotal) {
        this.idAvaliacao = idAvaliacao;
        this.idAluno = idAluno;
        this.dataRealizacao = dataRealizacao;
        this.notaTotal = notaTotal;
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public BigDecimal getNotaTotal() {
        return notaTotal;
    }

    public void setNotaTotal(BigDecimal notaTotal) {
        this.notaTotal = notaTotal;
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

    public String getTituloAvaliacao() {
        return tituloAvaliacao;
    }

    public void setTituloAvaliacao(String tituloAvaliacao) {
        this.tituloAvaliacao = tituloAvaliacao;
    }
}
