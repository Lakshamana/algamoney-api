package com.arjuna.algamoney.algamoneyapi.service;

import com.arjuna.algamoney.algamoneyapi.model.Lancamento;
import com.arjuna.algamoney.algamoneyapi.model.Pessoa;
import com.arjuna.algamoney.algamoneyapi.repository.LancamentoRepository;
import com.arjuna.algamoney.algamoneyapi.repository.PessoaRepository;
import com.arjuna.algamoney.algamoneyapi.service.exception.PessoaInvalidStateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private LancamentoRepository lancamentoRepository;

  public Lancamento salvar(Lancamento lancamento) {
    Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
    if (pessoa == null || !pessoa.isAtivo()) {
      throw new PessoaInvalidStateException();
    }
    return lancamentoRepository.save(lancamento);
  }
}
