
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

import br.com.codiub.estacionamento.entity.EstacionamentoCondutores;
import br.com.codiub.estacionamento.filter.EstacionamentoCondutoresFilter;
import br.com.codiub.estacionamento.repository.EstacionamentoCondutoresRepository;
import br.com.codiub.estacionamento.service.EstacionamentoCondutoresService;

@RestController
@RequestMapping("/estacionamentoCondutores")
public class EstacionamentoCondutoresResource {

  @Autowired private EstacionamentoCondutoresRepository estacionamentoCondutoresRepository;

  @Autowired private EstacionamentoCondutoresService estacionamentoCondutoresService;

  @PostMapping
  public ResponseEntity<EstacionamentoCondutores> criar(
      @RequestBody EstacionamentoCondutores estacionamentoCondutores,
      HttpServletResponse response) {
    EstacionamentoCondutores estacionamentoCondutoresSalva =
        estacionamentoCondutoresRepository.save(estacionamentoCondutores);
    return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoCondutoresSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<EstacionamentoCondutores> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<EstacionamentoCondutores> estacionamentoCondutores =
        estacionamentoCondutoresRepository.findById(codigo);
    return estacionamentoCondutores != null
        ? ResponseEntity.ok(estacionamentoCondutores.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    estacionamentoCondutoresRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<EstacionamentoCondutores> atualizar(
      @PathVariable Long codigo,
      @Validated @RequestBody EstacionamentoCondutores estacionamentoCondutores) {
    EstacionamentoCondutores estacionamentoCondutoresSalva =
        estacionamentoCondutoresService.atualizar(codigo, estacionamentoCondutores);
    return ResponseEntity.ok(estacionamentoCondutoresSalva);
  }

  @GetMapping
  public Page<EstacionamentoCondutores> pesquisar(
      EstacionamentoCondutoresFilter estacionamentoCondutoresFilter, Pageable pageable) {
    return estacionamentoCondutoresRepository.filtrar(estacionamentoCondutoresFilter, pageable);
  }
  
  @GetMapping("/lista")
  public List<EstacionamentoCondutores> findAll(EstacionamentoCondutoresFilter filter) {
		return estacionamentoCondutoresRepository.filtrar(filter);
  }
  
}
