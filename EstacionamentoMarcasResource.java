
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

import br.com.codiub.estacionamento.entity.EstacionamentoMarcas;
import br.com.codiub.estacionamento.filter.EstacionamentoMarcasFilter;
import br.com.codiub.estacionamento.repository.EstacionamentoMarcasRepository;
import br.com.codiub.estacionamento.service.EstacionamentoMarcasService;

@RestController
@RequestMapping("/estacionamentoMarcas")
public class EstacionamentoMarcasResource {

  @Autowired private EstacionamentoMarcasRepository estacionamentoMarcasRepository;

  @Autowired private EstacionamentoMarcasService estacionamentoMarcasService;

  @PostMapping
  public ResponseEntity<EstacionamentoMarcas> criar(
      @RequestBody EstacionamentoMarcas estacionamentoMarcas, HttpServletResponse response) {
    EstacionamentoMarcas estacionamentoMarcasSalva =
        estacionamentoMarcasRepository.save(estacionamentoMarcas);
    return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoMarcasSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<EstacionamentoMarcas> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<EstacionamentoMarcas> estacionamentoMarcas =
        estacionamentoMarcasRepository.findById(codigo);
    return estacionamentoMarcas != null
        ? ResponseEntity.ok(estacionamentoMarcas.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    estacionamentoMarcasRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<EstacionamentoMarcas> atualizar(
      @PathVariable Long codigo,
      @Validated @RequestBody EstacionamentoMarcas estacionamentoMarcas) {
    EstacionamentoMarcas estacionamentoMarcasSalva =
        estacionamentoMarcasService.atualizar(codigo, estacionamentoMarcas);
    return ResponseEntity.ok(estacionamentoMarcasSalva);
  }

  @GetMapping
  public Page<EstacionamentoMarcas> pesquisar(
      EstacionamentoMarcasFilter estacionamentoMarcasFilter, Pageable pageable) {
    return estacionamentoMarcasRepository.filtrar(estacionamentoMarcasFilter, pageable);
  }
  
  @GetMapping("/lista")
 	public List<EstacionamentoMarcas> findAll(EstacionamentoMarcasFilter filter) {
 		return estacionamentoMarcasRepository.filtrar(filter);
 	}
}
