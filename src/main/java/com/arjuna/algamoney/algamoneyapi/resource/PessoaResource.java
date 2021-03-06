package com.arjuna.algamoney.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.arjuna.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.arjuna.algamoney.algamoneyapi.model.Pessoa;
import com.arjuna.algamoney.algamoneyapi.repository.PessoaRepository;
import com.arjuna.algamoney.algamoneyapi.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

  @Autowired
  private PessoaRepository repository;

  @Autowired
  private PessoaService service;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Pessoa> listar() {
    return repository.findAll();
  }

  @PostMapping
  public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody Pessoa p, HttpServletResponse response) {
    Pessoa pessoaSalva = repository.save(p);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(p);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
    Pessoa p = repository.findOne(codigo);
    return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
    Pessoa pessoaSalva = service.atualizar(codigo, pessoa);
    return ResponseEntity.ok(pessoaSalva);
  }

  @PutMapping("/{codigo}/ativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void atualizarAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
    service.atualizarAtivo(codigo, ativo);
  }

  @DeleteMapping("/{codigo}")
  public void deletar(@PathVariable Long codigo) {
    repository.delete(codigo);
  }
}
