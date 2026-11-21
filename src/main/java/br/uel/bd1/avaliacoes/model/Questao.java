package br.uel.bd1.avaliacoes.model;

import java.util.ArrayList;
import java.util.List;

public class Questao {
    private Integer idQuestao;
    private String enunciado;
    private String tipoQuestao; // 'OBJETIVA' ou 'DISCURSIVA'
    private String respostaEsperada;
    private List<Alternativa> alternativas = new ArrayList<>();

    public Questao() {}

    public Questao(Integer idQuestao, String enunciado, String tipoQuestao, String respostaEsperada) {
        this.idQuestao = idQuestao;
        this.enunciado = enunciado;
        this.tipoQuestao = tipoQuestao;
        this.respostaEsperada = respostaEsperada;
    }

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
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

    public String getRespostaEsperada() {
        return respostaEsperada;
    }

    public void setRespostaEsperada(String respostaEsperada) {
        this.respostaEsperada = respostaEsperada;
    }

    public List<Alternativa> getAlternativa() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }
}
