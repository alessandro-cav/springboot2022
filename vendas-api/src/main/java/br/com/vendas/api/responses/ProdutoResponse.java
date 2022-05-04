package br.com.vendas.api.responses;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idProduto;
	
	private String codigo;
	
	private String nome;
	
	private String descricao;
	
	private BigDecimal preco;
}
