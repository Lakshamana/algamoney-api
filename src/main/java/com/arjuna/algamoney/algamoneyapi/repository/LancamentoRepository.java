package com.arjuna.algamoney.algamoneyapi.repository;

import com.arjuna.algamoney.algamoneyapi.model.Lancamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
