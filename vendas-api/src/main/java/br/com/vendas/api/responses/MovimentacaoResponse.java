package br.com.vendas.api.responses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.vendas.api.enuns.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long idMovimentacao;

	private LocalDateTime dataMovimentacao;

	private TipoMovimentacao tipoMovimentacao;

	private BigDecimal valor;
}
