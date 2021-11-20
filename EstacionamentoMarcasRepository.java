
package br.com.codiub.estacionamento.repository;

import br.com.codiub.estacionamento.entity.EstacionamentoMarcas;
import br.com.codiub.estacionamento.repository.estacionamentoMarcas.EstacionamentoMarcasRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoMarcasRepository
    extends JpaRepository<EstacionamentoMarcas, Long>, EstacionamentoMarcasRepositoryQuery {}
