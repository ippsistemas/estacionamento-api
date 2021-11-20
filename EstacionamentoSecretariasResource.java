
package br.com.codiub.estacionamento.resource;

import java.util.List;
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

import br.com.codiub.estacionamento.entity.EstacionamentoSecretarias;
import br.com.codiub.estacionamento.filter.EstacionamentoSecretariasFilter;
import br.com.codiub.estacionamento.repository.EstacionamentoSecretariasRepository;
import br.com.codiub.estacionamento.service.EstacionamentoSecretariasService;

@RestController
@RequestMapping("/estacionamentoSecretarias")
public class EstacionamentoSecretariasResource {

  @Autowired private EstacionamentoSecretariasRepository estacionamentoSecretariasRepository;

  @Autowired private EstacionamentoSecretariasService estacionamentoSecretariasService;

  @PostMapping
  public ResponseEntity<EstacionamentoSecretarias> criar(
      @RequestBody EstacionamentoSecretarias estacionamentoSecretarias,
      HttpServletResponse response) {
    EstacionamentoSecretarias estacionamentoSecretariasSalva =
        estacionamentoSecretariasRepository.save(estacionamentoSecretarias);
    return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoSecretariasSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<EstacionamentoSecretarias> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<EstacionamentoSecretarias> estacionamentoSecretarias =
        estacionamentoSecretariasRepository.findById(codigo);
    return estacionamentoSecretarias != null
        ? ResponseEntity.ok(estacionamentoSecretarias.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    estacionamentoSecretariasRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<EstacionamentoSecretarias> atualizar(
      @PathVariable Long codigo,
      @Validated @RequestBody EstacionamentoSecretarias estacionamentoSecretarias) {
    EstacionamentoSecretarias estacionamentoSecretariasSalva =
        estacionamentoSecretariasService.atualizar(codigo, estacionamentoSecretarias);
    return ResponseEntity.ok(estacionamentoSecretariasSalva);
  }

  @GetMapping
  public Page<EstacionamentoSecretarias> pesquisar(
      EstacionamentoSecretariasFilter estacionamentoSecretariasFilter, Pageable pageable) {
    return estacionamentoSecretariasRepository.filtrar(estacionamentoSecretariasFilter, pageable);
  }
  
  @GetMapping("/lista")
  public List<EstacionamentoSecretarias> findAll(EstacionamentoSecretariasFilter filter) {
		return estacionamentoSecretariasRepository.filtrar(filter);
  }
}
