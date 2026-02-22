package com.test.projeto_full.controller;


import com.test.projeto_full.domain.Tecnico;
import com.test.projeto_full.domain.dtos.TecnicoDTO;
import com.test.projeto_full.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));


    }
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {

        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = (List<TecnicoDTO>) list.stream().map(obj -> new TecnicoDTO(obj).collect(Collectors.toList()));

        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@RequestBody TecnicoDTO objTDO) {

        Tecnico newObj = service.create(objTDO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(null).build();


    }
}