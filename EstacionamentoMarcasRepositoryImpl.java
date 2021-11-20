
package br.com.codiub.estacionamento.repository.estacionamentoMarcas;

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

import br.com.codiub.estacionamento.entity.EstacionamentoMarcas;
import br.com.codiub.estacionamento.entity.EstacionamentoMarcas_;
import br.com.codiub.estacionamento.filter.EstacionamentoMarcasFilter;

public class EstacionamentoMarcasRepositoryImpl implements EstacionamentoMarcasRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<EstacionamentoMarcas> filtrar(
      EstacionamentoMarcasFilter estacionamentoMarcasFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoMarcas> criteria = builder.createQuery(EstacionamentoMarcas.class);
    Root<EstacionamentoMarcas> root = criteria.from(EstacionamentoMarcas.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(estacionamentoMarcasFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<EstacionamentoMarcas> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(estacionamentoMarcasFilter));
  }
  
  @Override
  public List<EstacionamentoMarcas> filtrar(
		  EstacionamentoMarcasFilter estacionamentoMarcasFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoMarcas> criteria =
           builder.createQuery(EstacionamentoMarcas.class);
    Root<EstacionamentoMarcas> root = criteria.from(EstacionamentoMarcas.class);

    criteria.orderBy(builder.asc(root.get(EstacionamentoMarcas_.MARCA)));
    Predicate[] predicates = criarRestricoes(estacionamentoMarcasFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<EstacionamentoMarcas> query = manager.createQuery(criteria);
   
    return query.getResultList();
  }

  private Predicate[] criarRestricoes(
      EstacionamentoMarcasFilter estacionamentoMarcasFilter,
      CriteriaBuilder builder,
      Root<EstacionamentoMarcas> root) {
    List<Predicate> predicates = new ArrayList<>();

    // ID
    if (estacionamentoMarcasFilter.getId() != null) {
      predicates.add(
          builder.equal(root.get(EstacionamentoMarcas_.ID), estacionamentoMarcasFilter.getId()));
    }
    // MARCA
    if (StringUtils.hasLength(estacionamentoMarcasFilter.getMarca())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoMarcas_.MARCA)),
              "%" + estacionamentoMarcasFilter.getMarca().toLowerCase() + "%"));
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

  private Long total(EstacionamentoMarcasFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<EstacionamentoMarcas> root = criteria.from(EstacionamentoMarcas.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
