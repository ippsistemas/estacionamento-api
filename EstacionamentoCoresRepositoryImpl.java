package br.com.codiub.estacionamento.repository.estacionamentoCores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import br.com.codiub.estacionamento.entity.EstacionamentoCores;
import br.com.codiub.estacionamento.entity.EstacionamentoCores_;
import br.com.codiub.estacionamento.filter.EstacionamentoCoresFilter;

public class EstacionamentoCoresRepositoryImpl implements EstacionamentoCoresRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<EstacionamentoCores> filtrar(
     EstacionamentoCoresFilter estacionamentoCoresFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoCores> criteria = builder.createQuery(EstacionamentoCores.class);
    Root<EstacionamentoCores> root = criteria.from(EstacionamentoCores.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(estacionamentoCoresFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<EstacionamentoCores> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(estacionamentoCoresFilter));
  }

  @Override
  public List<EstacionamentoCores> filtrar(
		  EstacionamentoCoresFilter estacionamentoCoresFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoCores> criteria =
           builder.createQuery(EstacionamentoCores.class);
    Root<EstacionamentoCores> root = criteria.from(EstacionamentoCores.class);

    criteria.orderBy(builder.asc(root.get(EstacionamentoCores_.COR)));
    Predicate[] predicates = criarRestricoes(estacionamentoCoresFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<EstacionamentoCores> query = manager.createQuery(criteria);
   
    return query.getResultList();
  }
    
  
  
  private Predicate[] criarRestricoes(
      EstacionamentoCoresFilter estacionamentoCoresFilter,
      CriteriaBuilder builder,
      Root<EstacionamentoCores> root) {
    List<Predicate> predicates = new ArrayList<>();

    // COR
    if (StringUtils.hasLength(estacionamentoCoresFilter.getCor())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoCores_.COR)),
              "%" + estacionamentoCoresFilter.getCor().toLowerCase() + "%"));
    }

    // ID
    if (estacionamentoCoresFilter.getId() != null) {
      predicates.add(
          builder.equal(root.get(EstacionamentoCores_.ID), estacionamentoCoresFilter.getId()));
    }

    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
    int paginaAtual = pageable.getPageNumber();
    int totalRegistrosPorPagina = pageable.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPorPagina);
  }

  private Long total(EstacionamentoCoresFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<EstacionamentoCores> root = criteria.from(EstacionamentoCores.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
