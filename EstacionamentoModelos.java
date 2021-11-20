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
@Table(name = "ESTACIONAMENTO_MODELOS", schema = "DBO_INTERNET")
public class EstacionamentoModelos implements java.io.Serializable {

	private static final long serialVersionUID = 5169511956593841397L;

	@GenericGenerator(name = "SEQ_ESTACI_MODELOS", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = @Parameter(name = "sequence", value = "SEQ_ESTACI_MODELOS"))
	
	@Id
	@GeneratedValue(generator = "SEQ_ESTACI_MODELOS")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "MODELO", length = 80)
	private String modelo;
	
	@ManyToOne
	@JoinColumn(name = "MARCA")
	private EstacionamentoMarcas estacionamentoMarcas;

	

}
