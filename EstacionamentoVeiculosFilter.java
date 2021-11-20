
package br.com.codiub.estacionamento.filter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class EstacionamentoVeiculosFilter {
  @Size(max = 5)
  private Long ano;

  @NotNull
  @Size(min = 0, max = 20)
  private String codigoAdesivo;

  @Valid
  private EstacionamentoCondutoresFilter estacionamentoCondutoresFilter;
  
  private EstacionamentoCoresFilter estacionamentoCoresFilter;

  @NotNull
  @Size(min = 0, max = 10)
  private Long id;

  private EstacionamentoModelosFilter estacionamentoModelosFilter;

  @NotNull
  @Size(min = 0, max = 20)
  private String placa;
  
  @Size(min = 0, max = 240)
  private String observacao;

  @Valid private EstacionamentoStatusFilter estacionamentoStatusFilter;
}
