package br.com.vendas.api.responses;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPedido;

	private LocalDateTime dataPedido;

	private String numero;

}
