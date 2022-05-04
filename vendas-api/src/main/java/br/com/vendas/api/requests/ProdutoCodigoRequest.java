package br.com.vendas.api.requests;

import java.io.Serializable;

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
public class ProdutoCodigoRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "{codigo.not.blank}")
	@NotNull(message = "{codigo.not.null}")
	private String codigo;


}
