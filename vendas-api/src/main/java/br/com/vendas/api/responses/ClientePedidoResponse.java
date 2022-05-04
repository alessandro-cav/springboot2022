package br.com.vendas.api.responses;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientePedidoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCliente;

	private LocalDateTime dataCadastro;

	private String email;

	private String nome;
	
	private String cpfCnpj;

	private String telefone;
	
	private List<PedidoResponse> pedidos;

}

