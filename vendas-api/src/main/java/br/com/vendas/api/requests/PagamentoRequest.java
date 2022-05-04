package br.com.vendas.api.requests;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequest {

	@NotBlank(message = "Numero do pedido não pode ser em branco")
	@NotNull(message = "Numero do pedido é obrigatorio")
	private String numeroPedido;

	@CPF
	@NotNull(message = "{cpf.not.null}")
	private String cpf;

	@NotBlank(message = "Numero não pode ser em branco")
	@NotNull(message = "Numero é obrigatorio")
	private String numeroConta;

	@NotNull(message = "Valor é obrigatorio")
	private BigDecimal valor;

}
