
package br.com.codiub.estacionamento.repository.estacionamentoCondutores;

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

import br.com.codiub.estacionamento.entity.EstacionamentoCondutores;
import br.com.codiub.estacionamento.entity.EstacionamentoCondutores_;
import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias_;
import br.com.codiub.estacionamento.entity.EstacionamentoStatus_;
import br.com.codiub.estacionamento.filter.EstacionamentoCondutoresFilter;

public class EstacionamentoCondutoresRepositoryImpl
    implements EstacionamentoCondutoresRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<EstacionamentoCondutores> filtrar(
      EstacionamentoCondutoresFilter estacionamentoCondutoresFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoCondutores> criteria =
        builder.createQuery(EstacionamentoCondutores.class);
    Root<EstacionamentoCondutores> root = criteria.from(EstacionamentoCondutores.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(estacionamentoCondutoresFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<EstacionamentoCondutores> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(estacionamentoCondutoresFilter));
  }
  
  
  @Override
  public List<EstacionamentoCondutores> filtrar(
		  EstacionamentoCondutoresFilter estacionamentoCondutoresFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoCondutores> criteria =
           builder.createQuery(EstacionamentoCondutores.class);
    Root<EstacionamentoCondutores> root = criteria.from(EstacionamentoCondutores.class);

    criteria.orderBy(builder.asc(root.get(EstacionamentoCondutores_.CONDUTOR)));
    Predicate[] predicates = criarRestricoes(estacionamentoCondutoresFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<EstacionamentoCondutores> query = manager.createQuery(criteria);
   
    return query.getResultList();
  }
  

  private Predicate[] criarRestricoes(
      EstacionamentoCondutoresFilter estacionamentoCondutoresFilter,
      CriteriaBuilder builder,
      Root<EstacionamentoCondutores> root) {
    List<Predicate> predicates = new ArrayList<>();

    // CELULAR
    if (StringUtils.hasLength(estacionamentoCondutoresFilter.getCelular())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoCondutores_.CELULAR)),
              "%" + estacionamentoCondutoresFilter.getCelular().toLowerCase() + "%"));
    }

    // CONDUTOR
    if (StringUtils.hasLength(estacionamentoCondutoresFilter.getCondutor())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoCondutores_.CONDUTOR)),
              "%" + estacionamentoCondutoresFilter.getCondutor().toLowerCase() + "%"));
    }

    // CPF
    if (StringUtils.hasLength(estacionamentoCondutoresFilter.getCpf())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoCondutores_.CPF)),
              "%" + estacionamentoCondutoresFilter.getCpf().toLowerCase() + "%"));
    }

    // EMAIL
    if (StringUtils.hasLength(estacionamentoCondutoresFilter.getEmail())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoCondutores_.EMAIL)),
              "%" + estacionamentoCondutoresFilter.getEmail().toLowerCase() + "%"));
    }

    // ID
    if (estacionamentoCondutoresFilter.getId() != null) {
      predicates.add(
          builder.equal(
              root.get(EstacionamentoCondutores_.ID), estacionamentoCondutoresFilter.getId()));
    }
    // SECRETARIA
    if (estacionamentoCondutoresFilter.getEstacionamentoSecretariasFilter() != null) {
      // ID
      if (estacionamentoCondutoresFilter.getEstacionamentoSecretariasFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(EstacionamentoCondutores_.ESTACIONAMENTO_SECRETARIAS)
                    .get(EstacionamentoSecretarias_.ID),
                estacionamentoCondutoresFilter.getEstacionamentoSecretariasFilter().getId()));
      }
      // SECRETARIA
      if (StringUtils.hasLength(
          estacionamentoCondutoresFilter.getEstacionamentoSecretariasFilter().getSecretaria())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoCondutores_.ESTACIONAMENTO_SECRETARIAS)
                        .get(EstacionamentoSecretarias_.SECRETARIA)),
                "%"
                    + estacionamentoCondutoresFilter
                        .getEstacionamentoSecretariasFilter()
                        .getSecretaria()
                        .toLowerCase()
                    + "%"));
      }
    }

    // STATUS
    if (estacionamentoCondutoresFilter.getEstacionamentoStatusFilter() != null) {
      // ID
      if (estacionamentoCondutoresFilter.getEstacionamentoStatusFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(EstacionamentoCondutores_.ESTACIONAMENTO_STATUS)
                    .get(EstacionamentoStatus_.ID),
                estacionamentoCondutoresFilter.getEstacionamentoStatusFilter().getId()));
      }
      // STATUS
      if (StringUtils.hasLength(
          estacionamentoCondutoresFilter.getEstacionamentoStatusFilter().getStatus())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoCondutores_.ESTACIONAMENTO_STATUS)
                        .get(EstacionamentoStatus_.STATUS)),
                "%"
                    + estacionamentoCondutoresFilter
                        .getEstacionamentoStatusFilter()
                        .getStatus()
                        .toLowerCase()
                    + "%"));
      }
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

  private Long total(EstacionamentoCondutoresFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<EstacionamentoCondutores> root = criteria.from(EstacionamentoCondutores.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
