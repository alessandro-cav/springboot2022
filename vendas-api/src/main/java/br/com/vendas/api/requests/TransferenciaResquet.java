package br.com.vendas.api.requests;

import java.io.Serializable;
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
public class TransferenciaResquet implements Serializable {

	private static final long serialVersionUID = 1L;

	@CPF
	@NotNull(message = "Cpf do cliente origem é obrigatorio")
	private String clienteCpfOrigem;

	@NotBlank(message = "Numero da conta origem não pode ser em branco")
	@NotNull(message = "Numero da conta origem é obrigatorio")
	private String contaNumeroOrigem;

	@CPF
	@NotNull(message = "Cpf do cliente destino é obrigatorio")
	private String clienteCpfDestino;

	@NotBlank(message = "Numero da conta destino não pode ser em branco")
	@NotNull(message = "Numero da conta destino é obrigatorio")
	private String contaNumeroDestino;
	
	
	@NotNull(message = "Valor é obrigatorio")	
	private BigDecimal valor;

}
