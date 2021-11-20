package br.com.codiub.estacionamento.repository;

import br.com.codiub.estacionamento.entity.EstacionamentoCores;
import br.com.codiub.estacionamento.repository.estacionamentoCores.EstacionamentoCoresRepositoryQuery;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoCoresRepository
    extends JpaRepository<EstacionamentoCores, Long>, EstacionamentoCoresRepositoryQuery {}

	