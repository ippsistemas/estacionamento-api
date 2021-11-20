package br.com.codiub.estacionamento.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;

@Data
@Entity
@Table(name = "ESTACIONAMENTO_SECRETARIAS", schema = "DBO_INTERNET")
public class EstacionamentoSecretarias implements java.io.Serializable {

	private static final long serialVersionUID = -7586834207366180309L;
	
	
	@GenericGenerator(name = "SEQ_ESTACI_SECRETARIAS", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	parameters = @Parameter(name = "sequence", value = "SEQ_ESTACI_SECRETARIAS"))

    @Id
    @GeneratedValue(generator = "SEQ_ESTACI_SECRETARIAS")
    @Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "SECRETARIA", length = 80)
	private String secretaria;

	

}
