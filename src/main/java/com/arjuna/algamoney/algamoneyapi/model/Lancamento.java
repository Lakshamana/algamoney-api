package com.arjuna.algamoney.algamoneyapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lancamento")
public class Lancamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  private String descricao;

  @Column(name = "data_lancamento")
  private LocalDate dataLancamento;

  @Column(name = "data_vencimento")
  private LocalDate dataVencimento;

  private BigDecimal valor;

  private String observacao;

  @Enumerated(EnumType.STRING)
  private TipoLancamento tipo;

  @ManyToOne
  @JoinColumn(name = "codigo_pessoa")
  private Pessoa pessoa;

  @ManyToOne
  @JoinColumn(name = "codigo_categoria")
  private Categoria categoria;

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Lancamento)) {
      return false;
    }
    Lancamento lancamento = (Lancamento) o;
    return Objects.equals(codigo, lancamento.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }

  public Pessoa getPessoa() {
    return this.pessoa;
  }

  public void setPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
  }

  public Categoria getCategoria() {
    return this.categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public TipoLancamento getTipo() {
    return tipo;
  }

  public void setTipo(TipoLancamento tipo) {
    this.tipo = tipo;
  }

  public Long getCodigo() {
    return this.codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public LocalDate getDataLancamento() {
    return this.dataLancamento;
  }

  public void setDataLancamento(LocalDate dataLancamento) {
    this.dataLancamento = dataLancamento;
  }

  public LocalDate getDataVencimento() {
    return this.dataVencimento;
  }

  public void setDataVencimento(LocalDate dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getObservacao() {
    return this.observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }
}
