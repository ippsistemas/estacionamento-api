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

import br.com.codiub.estacionamento.entity.EstacionamentoCores;
import br.com.codiub.estacionamento.filter.EstacionamentoCoresFilter;
import br.com.codiub.estacionamento.repository.EstacionamentoCoresRepository;
import br.com.codiub.estacionamento.service.EstacionamentoCoresService;

@RestController
@RequestMapping("/estacionamentoCores")
public class EstacionamentoCoresResource {

  @Autowired private EstacionamentoCoresRepository estacionamentoCoresRepository;

  @Autowired private EstacionamentoCoresService estacionamentoCoresService;

  @PostMapping
  public ResponseEntity<EstacionamentoCores> criar(
      @RequestBody EstacionamentoCores estacionamentoCores, HttpServletResponse response) {
    EstacionamentoCores estacionamentoCoresSalva =
        estacionamentoCoresRepository.save(estacionamentoCores);
    return ResponseEntity.status(HttpStatus.CREATED).body(estacionamentoCoresSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<EstacionamentoCores> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<EstacionamentoCores> estacionamentoCores =
        estacionamentoCoresRepository.findById(codigo);
    return estacionamentoCores != null
        ? ResponseEntity.ok(estacionamentoCores.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    estacionamentoCoresRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<EstacionamentoCores> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody EstacionamentoCores estacionamentoCores) {
    EstacionamentoCores estacionamentoCoresSalva =
        estacionamentoCoresService.atualizar(codigo, estacionamentoCores);
    return ResponseEntity.ok(estacionamentoCoresSalva);
  }

  @GetMapping
  public Page<EstacionamentoCores> pesquisar(
      EstacionamentoCoresFilter estacionamentoCoresFilter, Pageable pageable) {
    return estacionamentoCoresRepository.filtrar(estacionamentoCoresFilter, pageable);
  }
  
  @GetMapping("/lista")
  public List<EstacionamentoCores> findAll(EstacionamentoCoresFilter filter) {
 		return estacionamentoCoresRepository.filtrar(filter);
  }
   
  
}
