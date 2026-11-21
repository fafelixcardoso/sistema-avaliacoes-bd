package br.uel.bd1.avaliacoes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Avaliacao {
    private Integer idAvaliacao;
    private String titulo;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer idProfessor;
    private String nomeProfessor;
    private List<AvaliacaoQuestao> questoes = new ArrayList<>();

    public Avaliacao() {}

    public Avaliacao(Integer idAvaliacao, String titulo, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim, Integer idProfessor) {
        this.idAvaliacao = idAvaliacao;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idProfessor = idProfessor;
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
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

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public List<AvaliacaoQuestao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<AvaliacaoQuestao> questoes) {
        this.questoes = questoes;
    }
}
