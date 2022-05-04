package br.com.vendas.api.responses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idEndereco;
	
	private String codigo;
	
	private String bairro;

	private String cidade;

	private String complemento;

	private String logradouro;

	private String numero;
	
}