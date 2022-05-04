package br.com.vendas.api.responses;

import java.math.BigDecimal;
import java.util.List;

import br.com.vendas.api.enuns.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaMovimentaoResponse {
	
	private Long idConta;
	
	private String numero;

	private BigDecimal saldo;

	private BigDecimal limite;
	
	private TipoConta tipoConta;
	
	private List<MovimentacaoResponse> movimentacoes;
}
