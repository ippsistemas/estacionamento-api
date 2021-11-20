package br.com.codiub.estacionamento.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;


@Data
@Entity
@Table(name = "ESTACIONAMENTO_VEICULOS", schema = "DBO_INTERNET")
public class EstacionamentoVeiculos implements java.io.Serializable {


	private static final long serialVersionUID = 0;
	
	
	@GenericGenerator(name = "SEQ_ESTACI_VEICULOS", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = @Parameter(name = "sequence", value = "SEQ_ESTACI_VEICULOS"))
	
	@Id
	@GeneratedValue(generator = "SEQ_ESTACI_VEICULOS")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "PLACA", nullable = false, length = 20)
	private String placa;
	
	@Column(name = "ANO", precision = 5, nullable = true, scale = 0)
	private Long ano;
	
		
	@ManyToOne
	@JoinColumn(name = "MODELO", nullable = true)
	private EstacionamentoModelos estacionamentoModelos;
	
	@ManyToOne
	@JoinColumn(name = "COR", nullable = true)
	private EstacionamentoCores estacionamentoCores;
		
	
	@ManyToOne
	@JoinColumn(name = "CONDUTOR",  nullable = false)
	private EstacionamentoCondutores estacionamentoCondutores;
	
	@ManyToOne
	@JoinColumn(name = "STATUS",  nullable = false)
	private EstacionamentoStatus estacionamentoStatus;
	
	@Column(name = "CODIGO_ADESIVO", nullable = false, length = 20)
	private String codigoAdesivo;
	
	@Column(name = "OBSERVACAO", nullable = true, length = 255)
	private String observacao;

	
}
