package br.com.vendas.api.requests;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class ContaMovimentacaoRequest  implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Valor é obrigatorio")
	private BigDecimal valor;
	
	@CPF
	@NotNull(message = "Cpf é obrigatorio")
	private String cpf;
	
	@NotBlank(message = "Numero não pode ser em branco")
	@NotNull(message = "Numero é obrigatorio")
	private String numero;
	
}

