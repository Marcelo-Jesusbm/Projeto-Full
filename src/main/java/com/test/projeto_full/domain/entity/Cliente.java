package com.test.projeto_full.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.projeto_full.domain.Enums.Perfil;
import com.test.projeto_full.domain.dtos.ClienteDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;



@Entity
public class Cliente extends Pessoa {

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente() {
       super();
       addPerfil(Perfil.CLIENTE);
    }

    public Cliente(ClienteDTO objDTO) {
        super();
        this.id = objDTO.getId();
        this.nome = objDTO.getNome();
        this.cpf = objDTO.getCpf();
        this.email = objDTO.getEmail();
        this.senha = objDTO.getSenha();
        this.perfis = objDTO.getPerfis().stream().map(Perfil::getCodigo).collect(java.util.stream.Collectors.toSet());
        this.datacriacao = objDTO.getDatacriacao();
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}
