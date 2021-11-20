
package br.com.codiub.estacionamento.service;

import br.com.codiub.estacionamento.entity.EstacionamentoCondutores;
import br.com.codiub.estacionamento.repository.EstacionamentoCondutoresRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstacionamentoCondutoresService {

  @Autowired private EstacionamentoCondutoresRepository estacionamentoCondutoresRepository;

  public EstacionamentoCondutores atualizar(
      Long codigo, EstacionamentoCondutores estacionamentoCondutores) {
    EstacionamentoCondutores estacionamentoCondutoresSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(estacionamentoCondutores, estacionamentoCondutoresSalva, "id");
    return estacionamentoCondutoresRepository.save(estacionamentoCondutoresSalva);
  }

  public EstacionamentoCondutores buscarPeloCodigo(Long codigo) {
    Optional<EstacionamentoCondutores> estacionamentoCondutoresSalva =
        estacionamentoCondutoresRepository.findById(codigo);
    if (estacionamentoCondutoresSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return estacionamentoCondutoresSalva.get();
  }
}
