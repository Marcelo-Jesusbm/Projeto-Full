package com.test.projeto_full.repositories;

import com.test.projeto_full.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {



}
