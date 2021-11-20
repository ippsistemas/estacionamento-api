
package br.com.codiub.estacionamento.filter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class EstacionamentoCondutoresFilter {
  @NotNull
  @Size(min = 0, max = 20)
  private String celular;

  @NotNull
  @Size(min = 0, max = 160)
  private String condutor;

  
  @Size(min = 0, max = 20)
  private String cpf;

  @Size(max = 80)
  private String email;

  @NotNull
  @Size(min = 0, max = 22)
  private Long id;

  private EstacionamentoSecretariasFilter estacionamentoSecretariasFilter;
  private EstacionamentoStatusFilter estacionamentoStatusFilter;
  
}
