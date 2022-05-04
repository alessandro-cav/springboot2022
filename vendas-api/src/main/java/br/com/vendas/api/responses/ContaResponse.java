package br.com.vendas.api.responses;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.vendas.api.enuns.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idConta;
	
	private String numero;
	
	private BigDecimal saldo;
	
	private BigDecimal limite;
	
	private TipoConta  tipoConta;

}
