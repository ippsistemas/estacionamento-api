
package br.com.codiub.estacionamento.repository.estacionamentoSecretarias;

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

import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias;
import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias_;
import br.com.codiub.estacionamento.filter.EstacionamentoSecretariasFilter;


public class EstacionamentoSecretariasRepositoryImpl
    implements EstacionamentoSecretariasRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<EstacionamentoSecretarias> filtrar(
      EstacionamentoSecretariasFilter estacionamentoSecretariasFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoSecretarias> criteria =
        builder.createQuery(EstacionamentoSecretarias.class);
    Root<EstacionamentoSecretarias> root = criteria.from(EstacionamentoSecretarias.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(estacionamentoSecretariasFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<EstacionamentoSecretarias> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(estacionamentoSecretariasFilter));
  }
  
  
  @Override
  public List<EstacionamentoSecretarias> filtrar(
		  EstacionamentoSecretariasFilter estacionamentoSecretariasFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoSecretarias> criteria =
           builder.createQuery(EstacionamentoSecretarias.class);
    Root<EstacionamentoSecretarias> root = criteria.from(EstacionamentoSecretarias.class);

    criteria.orderBy(builder.asc(root.get(EstacionamentoSecretarias_.SECRETARIA)));
    Predicate[] predicates = criarRestricoes(estacionamentoSecretariasFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<EstacionamentoSecretarias> query = manager.createQuery(criteria);
   
    return query.getResultList();
  }

  private Predicate[] criarRestricoes(
      EstacionamentoSecretariasFilter estacionamentoSecretariasFilter,
      CriteriaBuilder builder,
      Root<EstacionamentoSecretarias> root) {
    List<Predicate> predicates = new ArrayList<>();

    // ID
    if (estacionamentoSecretariasFilter.getId() != null) {
      predicates.add(
          builder.equal(
              root.get(EstacionamentoSecretarias_.ID), estacionamentoSecretariasFilter.getId()));
    }
    // SECRETARIA
    if (StringUtils.hasLength(estacionamentoSecretariasFilter.getSecretaria())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoSecretarias_.SECRETARIA)),
              "%" + estacionamentoSecretariasFilter.getSecretaria().toLowerCase() + "%"));
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

  private Long total(EstacionamentoSecretariasFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<EstacionamentoSecretarias> root = criteria.from(EstacionamentoSecretarias.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
