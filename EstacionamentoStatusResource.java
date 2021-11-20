
package br.com.codiub.estacionamento.resource;

import br.com.codiub.estacionamento.entity.EstacionamentoStatus;
import br.com.codiub.estacionamento.filter.EstacionamentoStatusFilter;
import br.com.codiub.estacionamento.repository.EstacionamentoStatusRepository;
import br.com.codiub.estacionamento.service.EstacionamentoStatusService;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estacionamentoStatus")
public class EstacionamentoStatusResource {

  @Autowired private EstacionamentoStatusRepository estacionamentoStatusRepository;

  @Autowired private EstacionamentoStatusService estacionamentoStatusService;

  @PostMapping
  public ResponseEntity<EstacionamentoStatus> criar(
      @RequestBody EstacionamentoStatus estacionamentoStatus, HttpServletResponse response) {
    EstacionamentoStatus estacionamentoStatusSalva =
        estacionamentoStatusRepository.save(estacionamentoStatus);
    return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoStatusSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<EstacionamentoStatus> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<EstacionamentoStatus> estacionamentoStatus =
        estacionamentoStatusRepository.findById(codigo);
    return estacionamentoStatus != null
        ? ResponseEntity.ok(estacionamentoStatus.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    estacionamentoStatusRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<EstacionamentoStatus> atualizar(
      @PathVariable Long codigo,
      @Validated @RequestBody EstacionamentoStatus estacionamentoStatus) {
    EstacionamentoStatus estacionamentoStatusSalva =
        estacionamentoStatusService.atualizar(codigo, estacionamentoStatus);
    return ResponseEntity.ok(estacionamentoStatusSalva);
  }

  @GetMapping
  public Page<EstacionamentoStatus> pesquisar(
      EstacionamentoStatusFilter estacionamentoStatusFilter, Pageable pageable) {
    return estacionamentoStatusRepository.filtrar(estacionamentoStatusFilter, pageable);
  }
}
