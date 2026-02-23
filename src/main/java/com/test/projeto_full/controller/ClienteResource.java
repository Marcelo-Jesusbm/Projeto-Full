package com.test.projeto_full.controller;


import com.test.projeto_full.domain.dtos.ClienteDTO;
import com.test.projeto_full.domain.dtos.TecnicoDTO;
import com.test.projeto_full.domain.entity.Cliente;
import com.test.projeto_full.domain.entity.Tecnico;
import com.test.projeto_full.services.ClienteService;
import com.test.projeto_full.services.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));


    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {

        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = (List<ClienteDTO>) list.stream().map(
                obj -> new ClienteDTO(obj).collect(Collectors.toList()));

        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objTDO) {

        Cliente newObj = service.create(objTDO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
                newObj.getId()).toUri();
        return ResponseEntity.created(null).build();


    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id,
                                             @Valid @RequestBody ClienteDTO objTDO) {

        Cliente obj = service.update(id, objTDO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));


    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}