package br.com.codiub.estacionamento.repository.estacionamentoCores;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.estacionamento.entity.EstacionamentoCores;
import br.com.codiub.estacionamento.filter.EstacionamentoCoresFilter;

public interface EstacionamentoCoresRepositoryQuery {

  public Page<EstacionamentoCores> filtrar(
      EstacionamentoCoresFilter estacionamentoCoresFilter, Pageable pageable);
 
  public List<EstacionamentoCores> filtrar(
		  EstacionamentoCoresFilter estacionamentoCoresFilter);

}