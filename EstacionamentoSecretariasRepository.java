
package br.com.codiub.estacionamento.repository;

import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias;
import br.com.codiub.estacionamento.repository.estacionamentoSecretarias.EstacionamentoSecretariasRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoSecretariasRepository
    extends JpaRepository<EstacionamentoSecretarias, Long>, EstacionamentoSecretariasRepositoryQuery {}
