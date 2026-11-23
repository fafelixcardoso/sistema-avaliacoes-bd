package br.uel.bd1.avaliacoes.model;

import java.math.BigDecimal;

public class RelatorioQuestaoDificilDTO {
    private Integer idQuestao;
    private String tipoQuestao;
    private String enunciadoResumido;
    private Long totalRespostasAvaliadas;
    private BigDecimal mediaObtida;
    private BigDecimal valorReferencia;
    private BigDecimal taxaAproveitamentoPct;
    private BigDecimal mediaGlobalSistema;
    private BigDecimal diferencaVsMediaGlobal;

    public RelatorioQuestaoDificilDTO() {}

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getTipoQuestao() {
        return tipoQuestao;
    }

    public void setTipoQuestao(String tipoQuestao) {
        this.tipoQuestao = tipoQuestao;
    }

    public String getEnunciadoResumido() {
        return enunciadoResumido;
    }

    public void setEnunciadoResumido(String enunciadoResumido) {
        this.enunciadoResumido = enunciadoResumido;
    }

    public Long getTotalRespostasAvaliadas() {
        return totalRespostasAvaliadas;
    }

    public void setTotalRespostasAvaliadas(Long totalRespostasAvaliadas) {
        this.totalRespostasAvaliadas = totalRespostasAvaliadas;
    }

    public BigDecimal getMediaObtida() {
        return mediaObtida;
    }

    public void setMediaObtida(BigDecimal mediaObtida) {
        this.mediaObtida = mediaObtida;
    }

    public BigDecimal getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(BigDecimal valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public BigDecimal getTaxaAproveitamentoPct() {
        return taxaAproveitamentoPct;
    }

    public void setTaxaAproveitamentoPct(BigDecimal taxaAproveitamentoPct) {
        this.taxaAproveitamentoPct = taxaAproveitamentoPct;
    }

    public BigDecimal getMediaGlobalSistema() {
        return mediaGlobalSistema;
    }

    public void setMediaGlobalSistema(BigDecimal mediaGlobalSistema) {
        this.mediaGlobalSistema = mediaGlobalSistema;
    }

    public BigDecimal getDiferencaVsMediaGlobal() {
        return diferencaVsMediaGlobal;
    }

    public void setDiferencaVsMediaGlobal(BigDecimal diferencaVsMediaGlobal) {
        this.diferencaVsMediaGlobal = diferencaVsMediaGlobal;
    }
}
