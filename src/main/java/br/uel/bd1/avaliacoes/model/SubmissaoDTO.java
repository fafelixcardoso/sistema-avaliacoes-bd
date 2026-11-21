package br.uel.bd1.avaliacoes.model;

import java.util.ArrayList;
import java.util.List;

public class SubmissaoDTO {
    private Integer idAvaliacao;
    private Integer idAluno;
    private List<ItemRespostaDTO> respostas = new ArrayList<>();

    public static class ItemRespostaDTO {
        private Integer idQuestao;
        private String letraEscolhida;
        private String textoResposta;

        public Integer getIdQuestao() {
            return idQuestao;
        }

        public void setIdQuestao(Integer idQuestao) {
            this.idQuestao = idQuestao;
        }

        public String getLetraEscolhida() {
            return letraEscolhida;
        }

        public void setLetraEscolhida(String letraEscolhida) {
            this.letraEscolhida = letraEscolhida;
        }

        public String getTextoResposta() {
            return textoResposta;
        }

        public void setTextoResposta(String textoResposta) {
            this.textoResposta = textoResposta;
        }
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

    public List<ItemRespostaDTO> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<ItemRespostaDTO> respostas) {
        this.respostas = respostas;
    }
}
