package br.uel.bd1.avaliacoes.model;

import java.math.BigDecimal;

public class RelatorioDesempenhoDTO {
    private Integer idAvaliacao;
    private String avaliacao;
    private String professorResponsavel;
    private Long totalRespostas;
    private BigDecimal mediaNota;
    private BigDecimal maiorNota;
    private BigDecimal menorNota;
    private BigDecimal taxaAdesaoPct;

    public RelatorioDesempenhoDTO() {}

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

    public String getProfessorResponsavel() {
        return professorResponsavel;
    }

    public void setProfessorResponsavel(String professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }

    public Long getTotalRespostas() {
        return totalRespostas;
    }

    public void setTotalRespostas(Long totalRespostas) {
        this.totalRespostas = totalRespostas;
    }

    public BigDecimal getMediaNota() {
        return mediaNota;
    }

    public void setMediaNota(BigDecimal mediaNota) {
        this.mediaNota = mediaNota;
    }

    public BigDecimal getMaiorNota() {
        return maiorNota;
    }

    public void setMaiorNota(BigDecimal maiorNota) {
        this.maiorNota = maiorNota;
    }

    public BigDecimal getMenorNota() {
        return menorNota;
    }

    public void setMenorNota(BigDecimal menorNota) {
        this.menorNota = menorNota;
    }

    public BigDecimal getTaxaAdesaoPct() {
        return taxaAdesaoPct;
    }

    public void setTaxaAdesaoPct(BigDecimal taxaAdesaoPct) {
        this.taxaAdesaoPct = taxaAdesaoPct;
    }
}
