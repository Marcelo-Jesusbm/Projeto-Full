package com.test.projeto_full.services;


import com.test.projeto_full.domain.entity.Pessoa;
import com.test.projeto_full.domain.entity.Tecnico;
import com.test.projeto_full.domain.dtos.TecnicoDTO;
import com.test.projeto_full.exceptions.DataIntegrityViolationException;
import com.test.projeto_full.exceptions.ObjectNotFoundException;
import com.test.projeto_full.repositories.PessoaRepository;
import com.test.projeto_full.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {

        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);

    }
    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }

        public Tecnico update(Integer id, TecnicoDTO objDTO) {
            objDTO.setId(id);
            Tecnico oldObj = findById(id);
            validaPorCpfEEmail(objDTO);
            oldObj = new Tecnico(objDTO);
            return repository.save(oldObj);


    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }
            repository.deleteById(id);
        }
    }
