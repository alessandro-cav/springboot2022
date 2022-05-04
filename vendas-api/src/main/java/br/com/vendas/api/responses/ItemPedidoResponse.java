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
public class ItemPedidoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCliente;

	private String nome;

	private String cpf;

	private String email;

	private String telefone;

	private LocalDateTime dataCadastro;

	private Long idPedido;

	private LocalDateTime dataPedido;

	private String numero;

	private List<ProdutoResponse> produtos;

}
