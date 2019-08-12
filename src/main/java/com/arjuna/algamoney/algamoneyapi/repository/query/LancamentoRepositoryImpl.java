package com.arjuna.algamoney.algamoneyapi.repository.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.arjuna.algamoney.algamoneyapi.model.Lancamento;
import com.arjuna.algamoney.algamoneyapi.model.Lancamento_;
import com.arjuna.algamoney.algamoneyapi.repository.filter.LancamentoFilter;

import org.springframework.util.StringUtils;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<Lancamento> filtrar(LancamentoFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
    Root<Lancamento> root = criteria.from(Lancamento.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);
    TypedQuery<Lancamento> query = manager.createQuery(criteria);
    return query.getResultList();
  }

  private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
    List<Predicate> predicates = new ArrayList<>();
    if (!StringUtils.isEmpty(filter.getDescricao())) {
      predicates.add(builder.like(
        builder.lower(root.get(Lancamento_.descricao)),
        "%" + filter.getDescricao().toLowerCase() + "%"
      ));
    }

    if (filter.getDataVencimentoDe() != null) {
      predicates.add(null);
    }

    if (filter.getDataVencimentoAte() != null) {
      predicates.add(null);
    }
    return predicates.toArray(new Predicate[predicates.size()]);
  }
}
