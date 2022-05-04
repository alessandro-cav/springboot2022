package br.com.vendas.api.requests;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "{nome.not.blank}")
	@NotNull(message = "{nome.not.null}")
	private String nome;
	
	@NotBlank(message = "{codigo.not.blank}")
	@NotNull(message = "{codigo.not.null}")
	private String codigo;

	@NotBlank(message = "{descricao.not.blank}")
	@NotNull(message = "{descricao.not.null}")
	private String descricao;

	@NotNull(message = "{preco.not.null}")
	private BigDecimal preco;
}

