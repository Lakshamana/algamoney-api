package com.arjuna.algamoney.algamoneyapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvent extends ApplicationEvent {

  private HttpServletResponse response;
  private Long codigo;

  public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo){
    super(source);
    this.response = response;
    this.codigo = codigo;
  }

  public HttpServletResponse getResponse() {
    return this.response;
  }

  public Long getCodigo() {
    return this.codigo;
  }
}
