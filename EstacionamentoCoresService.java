
package br.com.codiub.estacionamento.service;

import br.com.codiub.estacionamento.entity.EstacionamentoCores;
import br.com.codiub.estacionamento.repository.EstacionamentoCoresRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstacionamentoCoresService {

  @Autowired private EstacionamentoCoresRepository estacionamentoCoresRepository;

  public EstacionamentoCores atualizar(Long codigo, EstacionamentoCores estacionamentoCores) {
    EstacionamentoCores estacionamentoCoresSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(estacionamentoCores, estacionamentoCoresSalva, "id");
    return estacionamentoCoresRepository.save(estacionamentoCoresSalva);
  }

  public EstacionamentoCores buscarPeloCodigo(Long codigo) {
    Optional<EstacionamentoCores> estacionamentoCoresSalva =
        estacionamentoCoresRepository.findById(codigo);
    if (estacionamentoCoresSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return estacionamentoCoresSalva.get();
  }
}
