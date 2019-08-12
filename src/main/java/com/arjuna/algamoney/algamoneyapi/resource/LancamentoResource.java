package com.arjuna.algamoney.algamoneyapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.arjuna.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.arjuna.algamoney.algamoneyapi.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.arjuna.algamoney.algamoneyapi.model.Lancamento;
import com.arjuna.algamoney.algamoneyapi.repository.LancamentoRepository;
import com.arjuna.algamoney.algamoneyapi.repository.filter.LancamentoFilter;
import com.arjuna.algamoney.algamoneyapi.service.LancamentoService;
import com.arjuna.algamoney.algamoneyapi.service.exception.PessoaInvalidStateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

  @Autowired
  private LancamentoRepository lancamentoRepository;

  @Autowired
  private LancamentoService lancamentoService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private MessageSource messageSource;

  @GetMapping
  public List<Lancamento> getLancamentos(LancamentoFilter filter) {
    return lancamentoRepository.findAll();
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Lancamento> getLancamento(@PathVariable Long codigo) {
    Lancamento lancamento = lancamentoRepository.findOne(codigo);
    return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Lancamento> createLancamento(@Valid @RequestBody Lancamento lancamento,
      HttpServletResponse response) {
    Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
  }

  @ExceptionHandler({ PessoaInvalidStateException.class })
  public ResponseEntity<Object> handlePessoaInvalidStateException(PessoaInvalidStateException e) {
    String userMessage = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
        LocaleContextHolder.getLocale());
    String devMessage = e.toString();
    List<Erro> erros = Arrays.asList(new Erro(userMessage, devMessage));
    return ResponseEntity.badRequest().body(erros);
  }
}
