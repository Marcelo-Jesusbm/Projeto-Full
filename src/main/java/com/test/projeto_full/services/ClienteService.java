package com.test.projeto_full.services;


import com.test.projeto_full.domain.dtos.ClienteDTO;
import com.test.projeto_full.domain.entity.Cliente;
import com.test.projeto_full.domain.entity.Pessoa;
import com.test.projeto_full.domain.entity.Tecnico;
import com.test.projeto_full.exceptions.DataIntegrityViolationException;
import com.test.projeto_full.exceptions.ObjectNotFoundException;
import com.test.projeto_full.repositories.ClienteRepository;
import com.test.projeto_full.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {

        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);

    }
    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }

        public Cliente update(Integer id, ClienteDTO objDTO) {
            objDTO.setId(id);
            Cliente oldObj = findById(id);
            validaPorCpfEEmail(objDTO);
            oldObj = new Cliente(objDTO);
            return repository.save(oldObj);


    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }
            repository.deleteById(id);
        }
    }
