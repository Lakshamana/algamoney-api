package com.arjuna.algamoney.algamoneyapi.repository;

import com.arjuna.algamoney.algamoneyapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {}
