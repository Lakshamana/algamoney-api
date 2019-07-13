package com.arjuna.algamoney.algamoneyapi.service;

import com.arjuna.algamoney.algamoneyapi.model.Pessoa;
import com.arjuna.algamoney.algamoneyapi.repository.PessoaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

  private final Logger log = LoggerFactory.getLogger(PessoaService.class);

  @Autowired
  private PessoaRepository repository;

  public Pessoa atualizar(Long codigo, Pessoa pessoa) {
    Pessoa pessoaSalva = repository.findOne(codigo);
    if (pessoaSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    log.info("Pessoa antes copy: {}", pessoaSalva.isAtivo());
    BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
    log.info("Pessoa depois copy: {}", pessoaSalva.isAtivo());
    return repository.save(pessoaSalva);
  }
}
