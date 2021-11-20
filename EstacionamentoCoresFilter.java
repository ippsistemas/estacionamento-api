package br.com.codiub.estacionamento.filter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class EstacionamentoCoresFilter {
  @Size(max = 20)
  private String cor;

  @NotNull
  @Size(min = 0, max = 10)
  private Long id;
}
