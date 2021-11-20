
package br.com.codiub.estacionamento.repository.estacionamentoSecretarias;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias;
import br.com.codiub.estacionamento.filter.EstacionamentoSecretariasFilter;

public interface EstacionamentoSecretariasRepositoryQuery {

  public Page<EstacionamentoSecretarias> filtrar(
      EstacionamentoSecretariasFilter estacionamentoSecretariasFilter, Pageable pageable);
  
  public List<EstacionamentoSecretarias> filtrar(
		  EstacionamentoSecretariasFilter estacionamentoSecretariasFilter);
}
