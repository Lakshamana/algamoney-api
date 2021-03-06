package com.arjuna.algamoney.algamoneyapi.exceptionhandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    String userMessage = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
    Throwable t = ex.getCause() != null ? ex.getCause() : ex;
    String devMessage = t.getMessage();
    List<Erro> erros = Arrays.asList(new Erro(userMessage, devMessage));
    return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({ EmptyResultDataAccessException.class })
  protected ResponseEntity<Object> handleEmptyResultDataAcessException(EmptyResultDataAccessException e,
      WebRequest request) {
    String userMessage = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
    String devMessage = e.getMessage();
    List<Erro> erros = Arrays.asList(new Erro(userMessage, devMessage));
    return handleExceptionInternal(e, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({ DataIntegrityViolationException.class })
  protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e,
      WebRequest request) {
    String userMessage = messageSource.getMessage("recurso.operacao-invalida", null, LocaleContextHolder.getLocale());
    String devMessage = ExceptionUtils.getRootCauseMessage(e);
    List<Erro> erros = Arrays.asList(new Erro(userMessage, devMessage));
    return handleExceptionInternal(e, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    List<Erro> erros = getErrorList(ex.getBindingResult());
    return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
  }

  private List<Erro> getErrorList(BindingResult bindingResult) {
    List<Erro> erros = bindingResult.getFieldErrors().stream().map(field -> {
      String userMessage = messageSource.getMessage(field, LocaleContextHolder.getLocale());
      String devMessage = field.toString();
      return new Erro(userMessage, devMessage);
    }).collect(Collectors.toList());
    return erros;
  }

  public static class Erro {
    private String userMessage;
    private String devMessage;

    public Erro(String userMessage, String devMessage) {
      this.devMessage = devMessage;
      this.userMessage = userMessage;
    }

    public String getUserMessage() {
      return userMessage;
    }

    public String getDevMessage() {
      return devMessage;
    }
  }
}
