package com.test.projeto_full.domain;

import com.test.projeto_full.Enums.Prioridade;
import com.test.projeto_full.Enums.Status;

import java.time.LocalDate;
import java.util.Objects;

public class Chamado {

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chamado chamado = (Chamado) o;
        return Objects.equals(id, chamado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private Integer id;
    private String titulo;
    private String observacoes;
    private Prioridade prioridade;
    private LocalDate dataAbertura = LocalDate.now();
    private LocalDate dataFechamento;
    private Status status;

    private Tecnico tecnico;
    private Cliente cliente;

    public Chamado() {
        super();
    }

    public Chamado(Integer id, String titulo, String observacoes, Prioridade prioridade, Status status, Tecnico tecnico, Cliente cliente) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.prioridade = prioridade;
        this.status = status;
        this.tecnico = tecnico;
        this.cliente = cliente;


    }



}
