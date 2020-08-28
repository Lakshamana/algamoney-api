package com.arjuna.algamoney.algamoneyapi.repository.lancamento;

import javax.persistence.EntityManager;

import com.arjuna.algamoney.algamoneyapi.model.Lancamento;
import com.arjuna.algamoney.algamoneyapi.repository.filter.LancamentoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {
  Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);

  EntityManager getPersistenceContext();
}
