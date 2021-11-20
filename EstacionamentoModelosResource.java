
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

import br.com.codiub.estacionamento.entity.EstacionamentoModelos;
import br.com.codiub.estacionamento.filter.EstacionamentoModelosFilter;
import br.com.codiub.estacionamento.repository.EstacionamentoModelosRepository;
import br.com.codiub.estacionamento.service.EstacionamentoModelosService;

@RestController
@RequestMapping("/estacionamentoModelos")
public class EstacionamentoModelosResource {

  @Autowired private EstacionamentoModelosRepository estacionamentoModelosRepository;

  @Autowired private EstacionamentoModelosService estacionamentoModelosService;

  @PostMapping
  public ResponseEntity<EstacionamentoModelos> criar(
      @RequestBody EstacionamentoModelos estacionamentoModelos, HttpServletResponse response) {
    EstacionamentoModelos estacionamentoModelosSalva =
        estacionamentoModelosRepository.save(estacionamentoModelos);
    return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoModelosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<EstacionamentoModelos> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<EstacionamentoModelos> estacionamentoModelos =
        estacionamentoModelosRepository.findById(codigo);
    return estacionamentoModelos != null
        ? ResponseEntity.ok(estacionamentoModelos.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    estacionamentoModelosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<EstacionamentoModelos> atualizar(
      @PathVariable Long codigo,
      @Validated @RequestBody EstacionamentoModelos estacionamentoModelos) {
    EstacionamentoModelos estacionamentoModelosSalva =
        estacionamentoModelosService.atualizar(codigo, estacionamentoModelos);
    return ResponseEntity.ok(estacionamentoModelosSalva);
  }

  @GetMapping
  public Page<EstacionamentoModelos> pesquisar(
      EstacionamentoModelosFilter estacionamentoModelosFilter, Pageable pageable) {
    return estacionamentoModelosRepository.filtrar(estacionamentoModelosFilter, pageable);
  }
  
  
  
  @GetMapping("/lista")
  public List<EstacionamentoModelos> findAll(EstacionamentoModelosFilter filter) {
		return estacionamentoModelosRepository.filtrar(filter);
  }
  
    
  
}
