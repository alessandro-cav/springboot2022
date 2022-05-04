package br.com.vendas.api.responses;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.vendas.api.enuns.SituacaoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoSituacaoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idSituacaoPedido;

	private LocalDateTime dataSituacao;

	private SituacaoPedido situacaoPedido;

}
