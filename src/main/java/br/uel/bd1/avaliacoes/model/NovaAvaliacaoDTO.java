package br.uel.bd1.avaliacoes.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NovaAvaliacaoDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer idProfessor;
    private List<ItemQuestaoDTO> questoes = new ArrayList<>();
    private List<Integer> alunosIds = new ArrayList<>();

    public static class ItemQuestaoDTO {
        private Integer idQuestao;
        private BigDecimal valorQuestao;
        private Integer numeroOrdem;

        public Integer getIdQuestao() {
            return idQuestao;
        }

        public void setIdQuestao(Integer idQuestao) {
            this.idQuestao = idQuestao;
        }

        public BigDecimal getValorQuestao() {
            return valorQuestao;
        }

        public void setValorQuestao(BigDecimal valorQuestao) {
            this.valorQuestao = valorQuestao;
        }

        public Integer getNumeroOrdem() {
            return numeroOrdem;
        }

        public void setNumeroOrdem(Integer numeroOrdem) {
            this.numeroOrdem = numeroOrdem;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public List<ItemQuestaoDTO> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<ItemQuestaoDTO> questoes) {
        this.questoes = questoes;
    }

    public List<Integer> getAlunosIds() {
        return alunosIds;
    }

    public void setAlunosIds(List<Integer> alunosIds) {
        this.alunosIds = alunosIds;
    }
}
