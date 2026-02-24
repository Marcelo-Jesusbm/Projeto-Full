package com.test.projeto_full.services;

import com.test.projeto_full.domain.Enums.Prioridade;
import com.test.projeto_full.domain.Enums.Status;
import com.test.projeto_full.domain.dtos.ChamadoDTO;
import com.test.projeto_full.domain.entity.Chamado;
import com.test.projeto_full.domain.entity.Cliente;
import com.test.projeto_full.domain.entity.Tecnico;
import com.test.projeto_full.exceptions.ObjectNotFoundException;
import com.test.projeto_full.repositories.ChamadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    @Autowired
    private TecnicoService service;

    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(@Valid ChamadoDTO objDTO) {
        return repository.save(newChamado(objDTO));
    }

    public Chamado update(Integer id, @Valid ChamadoDTO objTDO) {
        objTDO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objTDO);
        return repository.save(oldObj);
    }

    private Chamado newChamado(ChamadoDTO obj) {
        Tecnico tecnico = service.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Chamado chamado = new Chamado();
        if (obj.getId() != null) {
            chamado.setId(obj.getId());
        }
        if (obj.getStatus().equals(2)) {
            chamado.setDataFechamento(java.time.LocalDate.now());
        }

        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        chamado.setCliente(cliente);
        chamado.setTecnico(tecnico);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        return chamado;
    }


}





