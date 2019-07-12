package com.arjuna.algamoney.algamoneyapi.service;

import com.arjuna.algamoney.algamoneyapi.model.Pessoa;
import com.arjuna.algamoney.algamoneyapi.repository.PessoaRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

  @Autowired
  private PessoaRepository repository;

  public Pessoa atualizar(Long codigo, Pessoa pessoa) {
    Pessoa pessoaSalva = repository.findOne(codigo);
    if (pessoaSalva != null) {
      throw new EmptyResultDataAccessException(1);
    }
    BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
    return repository.save(pessoaSalva);
  }
}
