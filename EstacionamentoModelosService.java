
package br.com.codiub.estacionamento.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.estacionamento.entity.EstacionamentoModelos;
import br.com.codiub.estacionamento.repository.EstacionamentoModelosRepository;

@Service
public class EstacionamentoModelosService {

  @Autowired private EstacionamentoModelosRepository estacionamentoModelosRepository;

  public EstacionamentoModelos atualizar(Long codigo, EstacionamentoModelos estacionamentoModelos) {
    EstacionamentoModelos estacionamentoModelosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(estacionamentoModelos, estacionamentoModelosSalva, "id");
    return estacionamentoModelosRepository.save(estacionamentoModelosSalva);
  }

  public EstacionamentoModelos buscarPeloCodigo(Long codigo) {
    Optional<EstacionamentoModelos> estacionamentoModelosSalva = estacionamentoModelosRepository.findById(codigo);
    if (estacionamentoModelosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return estacionamentoModelosSalva.get();
  }

 
  
 

}
