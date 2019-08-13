package com.arjuna.algamoney.algamoneyapi.repository.lancamento;

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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder(); // builder
    CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class); // query template
    Root<Lancamento> root = criteria.from(Lancamento.class); // Root = Expression ('from' table)

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);
    TypedQuery<Lancamento> query = manager.createQuery(criteria);
    addPaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(filter));
  }

  private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
    List<Predicate> predicates = new ArrayList<>();
    if (!StringUtils.isEmpty(filter.getDescricao())) {
      predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
          "%" + filter.getDescricao().toLowerCase() + "%"));
    }

    if (filter.getDataVencimentoDe() != null) {
      predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
    }

    if (filter.getDataVencimentoAte() != null) {
      predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
    }
    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private void addPaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
    int currentPage = pageable.getPageNumber();
    int pageSize = pageable.getPageSize();
    int firstRegister = currentPage * pageSize;
    
    query.setFirstResult(firstRegister);
    query.setMaxResults(pageSize);
  }

  private Long total(LancamentoFilter lancamentoFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Lancamento> root = criteria.from(Lancamento.class);

    Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
    criteria.where(predicates);
    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();
  }
}
