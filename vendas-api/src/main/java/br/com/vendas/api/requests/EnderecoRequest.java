package br.com.vendas.api.requests;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@CPF
	@NotNull(message = "{cpf.not.null}")
	private String cpf;
	
	@NotBlank(message = "{codigo.not.blank}")
	@NotNull(message = "{codigo.not.null}")
	private String codigo;
	
	@NotBlank(message = "{bairro.not.blank}")
	@NotNull(message = "{bairro.not.null}")
	private String bairro;
	
	@NotBlank(message = "{cidade.not.blank}")
	@NotNull(message = "{cidade.not.null}")
	private String cidade;
	
	@NotBlank(message = "{complemento.not.blank}")
	@NotNull(message = "{complemento.not.null}")
	private String complemento;
	
	@NotBlank(message = "{logradouro.not.blank}")
	@NotNull(message = "{logradouro.not.null}")
	private String logradouro;
	
	@NotBlank(message = "{numero.not.blank}")
	@NotNull(message = "{numero.not.null}")
	private String numero;
}

