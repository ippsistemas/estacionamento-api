package br.com.codiub.estacionamento.service;

import br.com.codiub.estacionamento.entity.EstacionamentoStatus;
import br.com.codiub.estacionamento.repository.EstacionamentoStatusRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstacionamentoStatusService {

  @Autowired private EstacionamentoStatusRepository estacionamentoStatusRepository;

  public EstacionamentoStatus atualizar(Long codigo, EstacionamentoStatus estacionamentoStatus) {
    EstacionamentoStatus estacionamentoStatusSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(estacionamentoStatus, estacionamentoStatusSalva, "id");
    return estacionamentoStatusRepository.save(estacionamentoStatusSalva);
  }

  public EstacionamentoStatus buscarPeloCodigo(Long codigo) {
    Optional<EstacionamentoStatus> estacionamentoStatusSalva =
        estacionamentoStatusRepository.findById(codigo);
    if (estacionamentoStatusSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return estacionamentoStatusSalva.get();
  }
}
