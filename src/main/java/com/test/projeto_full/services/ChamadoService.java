package com.test.projeto_full.services;

import com.test.projeto_full.domain.entity.Chamado;
import com.test.projeto_full.exceptions.ObjectNotFoundException;
import com.test.projeto_full.repositories.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }








}
