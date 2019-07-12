package com.arjuna.algamoney.algamoneyapi.resource;

import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.arjuna.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.arjuna.algamoney.algamoneyapi.model.Categoria;
import com.arjuna.algamoney.algamoneyapi.repository.CategoriaRepository;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

  @Autowired
  private CategoriaRepository repository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Categoria> listar() {
    return repository.findAll();
  }

  @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria c, HttpServletResponse response){
      Categoria categoriaSalva = repository.save(c);
      publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
      return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

  @GetMapping("/{codigo}")
  public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo) {
    Categoria busca = repository.findOne(codigo);
    return busca != null ? ResponseEntity.ok(busca) : ResponseEntity.notFound().build();
  }
}
