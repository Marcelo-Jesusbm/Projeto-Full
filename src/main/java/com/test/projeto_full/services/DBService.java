package com.test.projeto_full.services;

import com.test.projeto_full.domain.entity.Chamado;
import com.test.projeto_full.domain.entity.Cliente;
import com.test.projeto_full.domain.Enums.Perfil;
import com.test.projeto_full.domain.Enums.Prioridade;
import com.test.projeto_full.domain.Enums.Status;
import com.test.projeto_full.domain.entity.Tecnico;
import com.test.projeto_full.repositories.ChamadoRepository;
import com.test.projeto_full.repositories.ClienteRepository;
import com.test.projeto_full.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;



    public void instanciaDB(){
        Tecnico tec1 = new Tecnico(null, "Maria", "12345678901", "maria@gmail.com", "123");
        tec1.addPerfil(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "João", "10987654321", "joao@gmail.com", "123");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);


        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));






    }






}
