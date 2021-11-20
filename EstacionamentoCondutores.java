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
@Table(name = "ESTACIONAMENTO_CONDUTORES", schema = "DBO_INTERNET")
public class EstacionamentoCondutores implements java.io.Serializable {
	
	private static final long serialVersionUID = 1003399523866013485L;
		
	@GenericGenerator(name = "SEQ_ESTACI_CONDUTORES", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = @Parameter(name = "sequence", value = "SEQ_ESTACI_CONDUTORES"))
	
	@Id
	@GeneratedValue(generator = "SEQ_ESTACI_CONDUTORES")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "CONDUTOR", nullable = false, length = 160)
	private String condutor;
	
	@Column(name = "CPF", nullable = true, length = 160)
	private String cpf;
	
	@Column(name = "CELULAR",  nullable = true, length = 20)
	private String celular;
	
	@Column(name = "EMAIL",  nullable = true, length = 80)
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "STATUS", nullable = true)
	private EstacionamentoStatus estacionamentoStatus;
	
	@ManyToOne
	@JoinColumn(name = "SECRETARIA",  nullable = true)
	private EstacionamentoSecretarias estacionamentoSecretarias;
	
	

}
