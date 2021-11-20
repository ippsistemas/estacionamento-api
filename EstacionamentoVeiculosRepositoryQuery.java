
package br.com.codiub.estacionamento.repository.estacionamentoVeiculos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.estacionamento.entity.EstacionamentoVeiculos;
import br.com.codiub.estacionamento.filter.EstacionamentoVeiculosFilter;

public interface EstacionamentoVeiculosRepositoryQuery {

  public Page<EstacionamentoVeiculos> filtrar(
      EstacionamentoVeiculosFilter estacionamentoVeiculosFilter, Pageable pageable);
  
  public List<EstacionamentoVeiculos> filtrar(
		  EstacionamentoVeiculosFilter estacionamentoVeiculosFilter);
  

}
