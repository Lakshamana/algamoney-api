package com.arjuna.algamoney.algamoneyapi.repository.lancamento;

import java.util.List;

import com.arjuna.algamoney.algamoneyapi.model.Lancamento;
import com.arjuna.algamoney.algamoneyapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
  List<Lancamento> filtrar(LancamentoFilter filter);
}
