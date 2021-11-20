
package br.com.codiub.estacionamento.service;

import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias;
import br.com.codiub.estacionamento.repository.EstacionamentoSecretariasRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstacionamentoSecretariasService {

  @Autowired private EstacionamentoSecretariasRepository estacionamentoSecretariasRepository;

  public EstacionamentoSecretarias atualizar(
      Long codigo, EstacionamentoSecretarias estacionamentoSecretarias) {
    EstacionamentoSecretarias estacionamentoSecretariasSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(estacionamentoSecretarias, estacionamentoSecretariasSalva, "id");
    return estacionamentoSecretariasRepository.save(estacionamentoSecretariasSalva);
  }

  public EstacionamentoSecretarias buscarPeloCodigo(Long codigo) {
    Optional<EstacionamentoSecretarias> estacionamentoSecretariasSalva =
        estacionamentoSecretariasRepository.findById(codigo);
    if (estacionamentoSecretariasSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return estacionamentoSecretariasSalva.get();
  }
}
