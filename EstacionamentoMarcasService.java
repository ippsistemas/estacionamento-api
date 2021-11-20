
package br.com.codiub.estacionamento.service;

import br.com.codiub.estacionamento.entity.EstacionamentoMarcas;
import br.com.codiub.estacionamento.repository.EstacionamentoMarcasRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstacionamentoMarcasService {

  @Autowired private EstacionamentoMarcasRepository estacionamentoMarcasRepository;

  public EstacionamentoMarcas atualizar(Long codigo, EstacionamentoMarcas estacionamentoMarcas) {
    EstacionamentoMarcas estacionamentoMarcasSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(estacionamentoMarcas, estacionamentoMarcasSalva, "id");
    return estacionamentoMarcasRepository.save(estacionamentoMarcasSalva);
  }

  public EstacionamentoMarcas buscarPeloCodigo(Long codigo) {
    Optional<EstacionamentoMarcas> estacionamentoMarcasSalva =
        estacionamentoMarcasRepository.findById(codigo);
    if (estacionamentoMarcasSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return estacionamentoMarcasSalva.get();
  }
}
