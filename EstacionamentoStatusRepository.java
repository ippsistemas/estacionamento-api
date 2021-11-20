
package br.com.codiub.estacionamento.repository;

import br.com.codiub.estacionamento.entity.EstacionamentoStatus;
import br.com.codiub.estacionamento.repository.estacionamentoStatus.EstacionamentoStatusRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoStatusRepository
    extends JpaRepository<EstacionamentoStatus, Long>, EstacionamentoStatusRepositoryQuery {}
