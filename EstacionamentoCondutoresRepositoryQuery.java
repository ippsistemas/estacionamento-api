
package br.com.codiub.estacionamento.repository.estacionamentoCondutores;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.estacionamento.entity.EstacionamentoCondutores;
import br.com.codiub.estacionamento.filter.EstacionamentoCondutoresFilter;

public interface EstacionamentoCondutoresRepositoryQuery {

  public Page<EstacionamentoCondutores> filtrar(
      EstacionamentoCondutoresFilter estacionamentoCondutoresFilter, Pageable pageable);
  
  public List<EstacionamentoCondutores> filtrar(
		  EstacionamentoCondutoresFilter estacionamentoCondutoresFilter);
}
