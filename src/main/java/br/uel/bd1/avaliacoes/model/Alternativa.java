package br.uel.bd1.avaliacoes.model;

public class Alternativa {
    private Integer idQuestao;
    private String letra; // 'A', 'B', 'C', 'D', 'E'
    private String textoAlternativa;
    private boolean ehCorreta;

    public Alternativa() {}

    public Alternativa(Integer idQuestao, String letra, String textoAlternativa, boolean ehCorreta) {
        this.idQuestao = idQuestao;
        this.letra = letra;
        this.textoAlternativa = textoAlternativa;
        this.ehCorreta = ehCorreta;
    }

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getTextoAlternativa() {
        return textoAlternativa;
    }

    public void setTextoAlternativa(String textoAlternativa) {
        this.textoAlternativa = textoAlternativa;
    }

    public boolean isEhCorreta() {
        return ehCorreta;
    }

    public void setEhCorreta(boolean ehCorreta) {
        this.ehCorreta = ehCorreta;
    }
}
