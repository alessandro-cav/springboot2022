package br.com.vendas.api.enuns;

public enum SituacaoPedido {

	PEDIDO_RECEBIDO("PEDIDO RECEBIDO"), PAGAMENTO_CONFIRMADO("PAGAMENTO CONFIRMADO"),
	PREPARANDO_PRODUTO("PREPARANDO PRODUTO"), PRODUTO_EM_TRANSPORTE("PRODUTO EM TRANSPORTE"),
	PRODUTO_ENTREGUE("PRODUTO ENTREGUE");

	private String descricao;
	
	
	public static SituacaoPedido getRequestType(String status) {
		SituacaoPedido pedido2 = null;
		for (SituacaoPedido pedido : SituacaoPedido.values()) {
			if (pedido.getDescricao().equals(status.toUpperCase())) {
				pedido2 =  pedido;
			}
		}
		return pedido2;
	}
	
	private SituacaoPedido(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
