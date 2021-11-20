
package br.com.codiub.estacionamento.filter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class EstacionamentoModelosFilter {
  @NotNull
  @Size(min = 0, max = 10)
  private Long id;

  @Valid private EstacionamentoMarcasFilter estacionamentoMarcasFilter;

  @NotNull
  @Size(min = 0, max = 80)
  private String modelo;
}
