package br.com.vendas.api.requests;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCpfRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@CPF
	@NotNull(message = "{cpf.not.null}")
	private String cpf;
}
