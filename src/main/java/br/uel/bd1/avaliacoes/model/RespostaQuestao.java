package br.uel.bd1.avaliacoes.model;

import java.math.BigDecimal;

public class RespostaQuestao {
    private Integer idAvaliacao;
    private Integer idAluno;
    private Integer idQuestao;
    private String textoResposta;
    private String letraEscolhida;
    private BigDecimal notaObtida;

    public RespostaQuestao() {}

    public RespostaQuestao(Integer idAvaliacao, Integer idAluno, Integer idQuestao, String textoResposta, String letraEscolhida, BigDecimal notaObtida) {
        this.idAvaliacao = idAvaliacao;
        this.idAluno = idAluno;
        this.idQuestao = idQuestao;
        this.textoResposta = textoResposta;
        this.letraEscolhida = letraEscolhida;
        this.notaObtida = notaObtida;
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

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getTextoResposta() {
        return textoResposta;
    }

    public void setTextoResposta(String textoResposta) {
        this.textoResposta = textoResposta;
    }

    public String getLetraEscolhida() {
        return letraEscolhida;
    }

    public void setLetraEscolhida(String letraEscolhida) {
        this.letraEscolhida = letraEscolhida;
    }

    public BigDecimal getNotaObtida() {
        return notaObtida;
    }

    public void setNotaObtida(BigDecimal notaObtida) {
        this.notaObtida = notaObtida;
    }
}
