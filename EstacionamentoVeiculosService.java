
package br.com.codiub.estacionamento.service;

import br.com.codiub.estacionamento.entity.EstacionamentoVeiculos;
import br.com.codiub.estacionamento.repository.EstacionamentoVeiculosRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstacionamentoVeiculosService {

  @Autowired private EstacionamentoVeiculosRepository estacionamentoVeiculosRepository;

  public EstacionamentoVeiculos atualizar(
      Long codigo, EstacionamentoVeiculos estacionamentoVeiculos) {
    EstacionamentoVeiculos estacionamentoVeiculosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(estacionamentoVeiculos, estacionamentoVeiculosSalva, "id");
    return estacionamentoVeiculosRepository.save(estacionamentoVeiculosSalva);
  }

  public EstacionamentoVeiculos buscarPeloCodigo(Long codigo) {
    Optional<EstacionamentoVeiculos> estacionamentoVeiculosSalva =
        estacionamentoVeiculosRepository.findById(codigo);
    if (estacionamentoVeiculosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return estacionamentoVeiculosSalva.get();
  }
}
