
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

import br.com.codiub.estacionamento.entity.EstacionamentoVeiculos;
import br.com.codiub.estacionamento.filter.EstacionamentoVeiculosFilter;
import br.com.codiub.estacionamento.repository.EstacionamentoVeiculosRepository;
import br.com.codiub.estacionamento.service.EstacionamentoVeiculosService;

@RestController
@RequestMapping("/estacionamentoVeiculos")
public class EstacionamentoVeiculosResource {

  @Autowired private EstacionamentoVeiculosRepository estacionamentoVeiculosRepository;

  @Autowired private EstacionamentoVeiculosService estacionamentoVeiculosService;

  @PostMapping
  public ResponseEntity<EstacionamentoVeiculos> criar(
      @RequestBody EstacionamentoVeiculos estacionamentoVeiculos, HttpServletResponse response) {
    EstacionamentoVeiculos estacionamentoVeiculosSalva =
        estacionamentoVeiculosRepository.save(estacionamentoVeiculos);
    return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoVeiculosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<EstacionamentoVeiculos> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<EstacionamentoVeiculos> estacionamentoVeiculos =
        estacionamentoVeiculosRepository.findById(codigo);
    return estacionamentoVeiculos != null
        ? ResponseEntity.ok(estacionamentoVeiculos.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    estacionamentoVeiculosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<EstacionamentoVeiculos> atualizar(
      @PathVariable Long codigo,
      @Validated @RequestBody EstacionamentoVeiculos estacionamentoVeiculos) {
    EstacionamentoVeiculos estacionamentoVeiculosSalva =
        estacionamentoVeiculosService.atualizar(codigo, estacionamentoVeiculos);
    return ResponseEntity.ok(estacionamentoVeiculosSalva);
  }

  @GetMapping
  public Page<EstacionamentoVeiculos> pesquisar(
      EstacionamentoVeiculosFilter estacionamentoVeiculosFilter, Pageable pageable) {
    return estacionamentoVeiculosRepository.filtrar(estacionamentoVeiculosFilter, pageable);
  }
  
  @GetMapping("/lista")
  public List<EstacionamentoVeiculos> findAll(EstacionamentoVeiculosFilter filter) {
 		return estacionamentoVeiculosRepository.filtrar(filter);
  }
  
   
   
}
