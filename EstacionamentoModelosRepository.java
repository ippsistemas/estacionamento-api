
package br.com.codiub.estacionamento.repository;

import br.com.codiub.estacionamento.entity.EstacionamentoModelos;
import br.com.codiub.estacionamento.repository.estacionamentoModelos.EstacionamentoModelosRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoModelosRepository
    extends JpaRepository<EstacionamentoModelos, Long>, EstacionamentoModelosRepositoryQuery {}
