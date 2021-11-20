
package br.com.codiub.estacionamento.repository.estacionamentoMarcas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.estacionamento.entity.EstacionamentoMarcas;
import br.com.codiub.estacionamento.filter.EstacionamentoMarcasFilter;

public interface EstacionamentoMarcasRepositoryQuery {

  public Page<EstacionamentoMarcas> filtrar(
      EstacionamentoMarcasFilter estacionamentoMarcasFilter, Pageable pageable);
  
  public List<EstacionamentoMarcas> filtrar(
		  EstacionamentoMarcasFilter estacionamentoMarcasFilter);
  
}
