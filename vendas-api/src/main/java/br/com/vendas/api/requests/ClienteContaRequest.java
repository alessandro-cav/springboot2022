package br.com.vendas.api.requests;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteContaRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@CPF
	@NotNull(message = "{cpf.not.null}")
	private String cpf;

	@NotBlank(message = "Numero não pode ser em branco")
	@NotNull(message = "Numero é obrigatorio")
	private String numero;

}
