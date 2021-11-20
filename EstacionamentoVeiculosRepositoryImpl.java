
package br.com.codiub.estacionamento.repository.estacionamentoVeiculos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.codiub.estacionamento.entity.EstacionamentoCondutores_;
import br.com.codiub.estacionamento.entity.EstacionamentoCores_;
import br.com.codiub.estacionamento.entity.EstacionamentoMarcas_;
import br.com.codiub.estacionamento.entity.EstacionamentoModelos_;
import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias_;
import br.com.codiub.estacionamento.entity.EstacionamentoStatus_;
import br.com.codiub.estacionamento.entity.EstacionamentoVeiculos;
import br.com.codiub.estacionamento.entity.EstacionamentoVeiculos_;
import br.com.codiub.estacionamento.filter.EstacionamentoVeiculosFilter;

public class EstacionamentoVeiculosRepositoryImpl implements EstacionamentoVeiculosRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<EstacionamentoVeiculos> filtrar(
      EstacionamentoVeiculosFilter estacionamentoVeiculosFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoVeiculos> criteria =
        builder.createQuery(EstacionamentoVeiculos.class);
    
    Root<EstacionamentoVeiculos> root = criteria.from(EstacionamentoVeiculos.class);

    //List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
    criteria.orderBy(builder.asc(root.get(EstacionamentoVeiculos_.CODIGO_ADESIVO)));
    Predicate[] predicates = criarRestricoes(estacionamentoVeiculosFilter, builder, root);
    //criteria.where(predicates).orderBy(orders);
    criteria.where(predicates);
    
    TypedQuery<EstacionamentoVeiculos> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(estacionamentoVeiculosFilter));
  }
  
  
  @Override
  public List<EstacionamentoVeiculos> filtrar(
      EstacionamentoVeiculosFilter estacionamentoVeiculosFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<EstacionamentoVeiculos> criteria =
        builder.createQuery(EstacionamentoVeiculos.class);
    Root<EstacionamentoVeiculos> root = criteria.from(EstacionamentoVeiculos.class);
    
    criteria.orderBy(builder.asc(root.get(EstacionamentoVeiculos_.CODIGO_ADESIVO)));
    
    Predicate[] predicates = criarRestricoes(estacionamentoVeiculosFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<EstacionamentoVeiculos> query = manager.createQuery(criteria);
    return query.getResultList();
  }
  

  private Predicate[] criarRestricoes(
      EstacionamentoVeiculosFilter estacionamentoVeiculosFilter,
      CriteriaBuilder builder,
      Root<EstacionamentoVeiculos> root) {
    List<Predicate> predicates = new ArrayList<>();

    // ANO
    if (estacionamentoVeiculosFilter.getAno() != null) {
      predicates.add(
          builder.equal(
              root.get(EstacionamentoVeiculos_.ANO), estacionamentoVeiculosFilter.getAno()));
    }
    // CODIGO_ADESIVO
    if (StringUtils.hasLength(estacionamentoVeiculosFilter.getCodigoAdesivo())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoVeiculos_.CODIGO_ADESIVO)),
              "%" + estacionamentoVeiculosFilter.getCodigoAdesivo().toLowerCase() + "%"));
    }

    // CONDUTOR
    if (estacionamentoVeiculosFilter.getEstacionamentoCondutoresFilter() != null) {
      // CELULAR
      if (StringUtils.hasLength(
          estacionamentoVeiculosFilter.getEstacionamentoCondutoresFilter().getCelular())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                        .get(EstacionamentoCondutores_.CELULAR)),
                "%"
                    + estacionamentoVeiculosFilter
                        .getEstacionamentoCondutoresFilter()
                        .getCelular()
                        .toLowerCase()
                    + "%"));
      }

      // CONDUTOR
      if (StringUtils.hasLength(
          estacionamentoVeiculosFilter.getEstacionamentoCondutoresFilter().getCondutor())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                        .get(EstacionamentoCondutores_.CONDUTOR)),
                "%"
                    + estacionamentoVeiculosFilter
                        .getEstacionamentoCondutoresFilter()
                        .getCondutor()
                        .toLowerCase()
                    + "%"));
      }

      // CPF
      if (StringUtils.hasLength(
          estacionamentoVeiculosFilter.getEstacionamentoCondutoresFilter().getCpf())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                        .get(EstacionamentoCondutores_.CPF)),
                "%"
                    + estacionamentoVeiculosFilter
                        .getEstacionamentoCondutoresFilter()
                        .getCpf()
                        .toLowerCase()
                    + "%"));
      }

      // EMAIL
      if (StringUtils.hasLength(
          estacionamentoVeiculosFilter.getEstacionamentoCondutoresFilter().getEmail())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                        .get(EstacionamentoCondutores_.EMAIL)),
                "%"
                    + estacionamentoVeiculosFilter
                        .getEstacionamentoCondutoresFilter()
                        .getEmail()
                        .toLowerCase()
                    + "%"));
      }

      // ID
      if (estacionamentoVeiculosFilter.getEstacionamentoCondutoresFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                    .get(EstacionamentoCondutores_.ID),
                estacionamentoVeiculosFilter.getEstacionamentoCondutoresFilter().getId()));
      }
      // SECRETARIA
      if (estacionamentoVeiculosFilter
              .getEstacionamentoCondutoresFilter()
              .getEstacionamentoSecretariasFilter()
          != null) {
        // ID
        if (estacionamentoVeiculosFilter
                .getEstacionamentoCondutoresFilter()
                .getEstacionamentoSecretariasFilter()
                .getId()
            != null) {
          predicates.add(
              builder.equal(
                  root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                      .get(EstacionamentoCondutores_.ESTACIONAMENTO_SECRETARIAS)
                      .get(EstacionamentoSecretarias_.ID),
                  estacionamentoVeiculosFilter
                      .getEstacionamentoCondutoresFilter()
                      .getEstacionamentoSecretariasFilter()
                      .getId()));
        }
        // SECRETARIA
        if (StringUtils.hasLength(
            estacionamentoVeiculosFilter
                .getEstacionamentoCondutoresFilter()
                .getEstacionamentoSecretariasFilter()
                .getSecretaria())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                          .get(EstacionamentoCondutores_.ESTACIONAMENTO_SECRETARIAS)
                          .get(EstacionamentoSecretarias_.SECRETARIA)),
                  "%"
                      + estacionamentoVeiculosFilter
                          .getEstacionamentoCondutoresFilter()
                          .getEstacionamentoSecretariasFilter()
                          .getSecretaria()
                          .toLowerCase()
                      + "%"));
        }
      }

      // STATUS
      if (estacionamentoVeiculosFilter
              .getEstacionamentoCondutoresFilter()
              .getEstacionamentoStatusFilter()
          != null) {
        // ID
        if (estacionamentoVeiculosFilter
                .getEstacionamentoCondutoresFilter()
                .getEstacionamentoStatusFilter()
                .getId()
            != null) {
          predicates.add(
              builder.equal(
                  root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                      .get(EstacionamentoCondutores_.ESTACIONAMENTO_STATUS)
                      .get(EstacionamentoStatus_.ID),
                  estacionamentoVeiculosFilter
                      .getEstacionamentoCondutoresFilter()
                      .getEstacionamentoStatusFilter()
                      .getId()));
        }
        // STATUS
        if (StringUtils.hasLength(
            estacionamentoVeiculosFilter
                .getEstacionamentoCondutoresFilter()
                .getEstacionamentoStatusFilter()
                .getStatus())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CONDUTORES)
                          .get(EstacionamentoCondutores_.ESTACIONAMENTO_STATUS)
                          .get(EstacionamentoStatus_.STATUS)),
                  "%"
                      + estacionamentoVeiculosFilter
                          .getEstacionamentoCondutoresFilter()
                          .getEstacionamentoStatusFilter()
                          .getStatus()
                          .toLowerCase()
                      + "%"));
        }
      }
        
    
    }

    // COR
    if (estacionamentoVeiculosFilter.getEstacionamentoCoresFilter() != null) {
      // COR
      if (StringUtils.hasLength(
          estacionamentoVeiculosFilter.getEstacionamentoCoresFilter().getCor())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CORES)
                        .get(EstacionamentoCores_.COR)),
                "%"
                    + estacionamentoVeiculosFilter
                        .getEstacionamentoCoresFilter()
                        .getCor()
                        .toLowerCase()
                    + "%"));
      }

      // ID
      if (estacionamentoVeiculosFilter.getEstacionamentoCoresFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_CORES).get(EstacionamentoCores_.ID),
                estacionamentoVeiculosFilter.getEstacionamentoCoresFilter().getId()));
      }
    }

    // ID
    if (estacionamentoVeiculosFilter.getId() != null) {
      predicates.add(
          builder.equal(
              root.get(EstacionamentoVeiculos_.ID), estacionamentoVeiculosFilter.getId()));
    }
    // MODELO
    if (estacionamentoVeiculosFilter.getEstacionamentoModelosFilter() != null) {
      // ID
      if (estacionamentoVeiculosFilter.getEstacionamentoModelosFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_MODELOS)
                    .get(EstacionamentoModelos_.ID),
                estacionamentoVeiculosFilter.getEstacionamentoModelosFilter().getId()));
      }
      // MARCA
      if (estacionamentoVeiculosFilter
              .getEstacionamentoModelosFilter()
              .getEstacionamentoMarcasFilter()
          != null) {
        // ID
        if (estacionamentoVeiculosFilter
                .getEstacionamentoModelosFilter()
                .getEstacionamentoMarcasFilter()
                .getId()
            != null) {
          predicates.add(
              builder.equal(
                  root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_MODELOS)
                      .get(EstacionamentoModelos_.ESTACIONAMENTO_MARCAS)
                      .get(EstacionamentoMarcas_.ID),
                  estacionamentoVeiculosFilter
                      .getEstacionamentoModelosFilter()
                      .getEstacionamentoMarcasFilter()
                      .getId()));
        }
        // MARCA
        if (StringUtils.hasLength(
            estacionamentoVeiculosFilter
                .getEstacionamentoModelosFilter()
                .getEstacionamentoMarcasFilter()
                .getMarca())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_MODELOS)
                          .get(EstacionamentoModelos_.ESTACIONAMENTO_MARCAS)
                          .get(EstacionamentoMarcas_.MARCA)),
                  "%"
                      + estacionamentoVeiculosFilter
                          .getEstacionamentoModelosFilter()
                          .getEstacionamentoMarcasFilter()
                          .getMarca()
                          .toLowerCase()
                      + "%"));
        }
      }

      // MODELO
      if (StringUtils.hasLength(
          estacionamentoVeiculosFilter.getEstacionamentoModelosFilter().getModelo())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_MODELOS)
                        .get(EstacionamentoModelos_.MODELO)),
                "%"
                    + estacionamentoVeiculosFilter
                        .getEstacionamentoModelosFilter()
                        .getModelo()
                        .toLowerCase()
                    + "%"));
      }
    }

    // PLACA
    if (StringUtils.hasLength(estacionamentoVeiculosFilter.getPlaca())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(EstacionamentoVeiculos_.PLACA)),
              "%" + estacionamentoVeiculosFilter.getPlaca().toLowerCase() + "%"));
    }

    // STATUS
    if (estacionamentoVeiculosFilter.getEstacionamentoStatusFilter() != null) {
      // ID
      if (estacionamentoVeiculosFilter.getEstacionamentoStatusFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_STATUS)
                    .get(EstacionamentoStatus_.ID),
                estacionamentoVeiculosFilter.getEstacionamentoStatusFilter().getId()));
      }
      // STATUS
      if (StringUtils.hasLength(
          estacionamentoVeiculosFilter.getEstacionamentoStatusFilter().getStatus())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(EstacionamentoVeiculos_.ESTACIONAMENTO_STATUS)
                        .get(EstacionamentoStatus_.STATUS)),
                "%"
                    + estacionamentoVeiculosFilter
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

  private Long total(EstacionamentoVeiculosFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<EstacionamentoVeiculos> root = criteria.from(EstacionamentoVeiculos.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }


 
  
}
