package br.uel.bd1.avaliacoes.model;

import java.math.BigDecimal;

public class AvaliacaoQuestao {
    private Integer idAvaliacao;
    private Integer idQuestao;
    private Integer numeroOrdem;
    private BigDecimal valorQuestao;

    // Campos informativos para visualização/telas
    private String enunciado;
    private String tipoQuestao;
    private java.util.List<Alternativa> alternativas = new java.util.ArrayList<>();

    public AvaliacaoQuestao() {}

    public AvaliacaoQuestao(Integer idAvaliacao, Integer idQuestao, Integer numeroOrdem, BigDecimal valorQuestao) {
        this.idAvaliacao = idAvaliacao;
        this.idQuestao = idQuestao;
        this.numeroOrdem = numeroOrdem;
        this.valorQuestao = valorQuestao;
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Integer getNumeroOrdem() {
        return numeroOrdem;
    }

    public void setNumeroOrdem(Integer numeroOrdem) {
        this.numeroOrdem = numeroOrdem;
    }

    public BigDecimal getValorQuestao() {
        return valorQuestao;
    }

    public void setValorQuestao(BigDecimal valorQuestao) {
        this.valorQuestao = valorQuestao;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipoQuestao() {
        return tipoQuestao;
    }

    public void setTipoQuestao(String tipoQuestao) {
        this.tipoQuestao = tipoQuestao;
    }

    public java.util.List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(java.util.List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }
}
