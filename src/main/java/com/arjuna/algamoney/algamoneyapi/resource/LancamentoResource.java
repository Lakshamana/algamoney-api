package com.arjuna.algamoney.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.arjuna.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.arjuna.algamoney.algamoneyapi.model.Lancamento;
import com.arjuna.algamoney.algamoneyapi.repository.LancamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

  @Autowired
  private LancamentoRepository lancamentoRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Lancamento> getLancamentos() {
    return lancamentoRepository.findAll();
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Lancamento> getLancamento(@PathVariable Long codigo) {
    Lancamento lancamento = lancamentoRepository.findOne(codigo);
    return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Lancamento> createLancamento(@RequestBody Lancamento lancamento, HttpServletResponse response) {
    Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
  }
}
