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
public class ClientePedidoPodutoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Codigo do produto não pode ser em branco")
	@NotNull(message = "Codigo do produto é obrigatorio")
	private String codigoProduto;
	
	@NotBlank(message = "Numero do pedido não pode ser em branco")
	@NotNull(message = "Numero do pedido é obrigatorio")
	private String numeroPedido;
	
	@CPF
	@NotNull(message = "Cpf é obrigatorio")
	private String cpf;
}