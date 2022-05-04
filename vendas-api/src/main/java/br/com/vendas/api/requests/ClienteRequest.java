package br.com.vendas.api.requests;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "{nome.not.blank}")
	@NotNull(message = "{nome.not.null}")
	private String nome;

	@Email
	@NotNull(message = "{email.not.null}")
	private String email;

	@CPF
	@NotNull(message = "{cpf.not.null}")
	private String cpf;

	@NotBlank(message = "{telefone.not.blank}")
	@NotNull(message = "{telefone.not.null}")
	private String telefone;
}
