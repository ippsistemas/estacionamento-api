
package br.com.codiub.estacionamento.repository;

import br.com.codiub.estacionamento.entity.EstacionamentoCondutores;
import br.com.codiub.estacionamento.repository.estacionamentoCondutores.EstacionamentoCondutoresRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoCondutoresRepository
    extends JpaRepository<EstacionamentoCondutores, Long>,
        EstacionamentoCondutoresRepositoryQuery {}
