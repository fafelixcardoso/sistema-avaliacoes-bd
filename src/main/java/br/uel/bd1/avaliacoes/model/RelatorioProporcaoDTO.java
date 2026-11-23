package br.uel.bd1.avaliacoes.model;

public class RelatorioProporcaoDTO {
    private String avaliacao;
    private Long totalRespondidas;
    private Long totalPendentes;

    public RelatorioProporcaoDTO() {}

    public RelatorioProporcaoDTO(String avaliacao, Long totalRespondidas, Long totalPendentes) {
        this.avaliacao = avaliacao;
        this.totalRespondidas = totalRespondidas;
        this.totalPendentes = totalPendentes;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Long getTotalRespondidas() {
        return totalRespondidas;
    }

    public void setTotalRespondidas(Long totalRespondidas) {
        this.totalRespondidas = totalRespondidas;
    }

    public Long getTotalPendentes() {
        return totalPendentes;
    }

    public void setTotalPendentes(Long totalPendentes) {
        this.totalPendentes = totalPendentes;
    }
}
