package br.com.codiub.estacionamento.repository;

import br.com.codiub.estacionamento.entity.EstacionamentoVeiculos;
import br.com.codiub.estacionamento.repository.estacionamentoVeiculos.EstacionamentoVeiculosRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoVeiculosRepository
    extends JpaRepository<EstacionamentoVeiculos, Long>, EstacionamentoVeiculosRepositoryQuery {}
