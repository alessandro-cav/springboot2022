package br.com.vendas.api.enuns;

public enum TipoMovimentacao {
	
	SAQUE("SAQUE"), DEPOSITO("DEPOSITO"), TRANSFERENCIA("TRANFERENCIA"), PAGAMENTO("PAGAMENTO");
	
	private String descricao;

	public TipoMovimentacao getMovementType(String tipo) {
		TipoMovimentacao tipoMovimentacao = null;
		
		for (TipoMovimentacao tipoMovimentacao2 : TipoMovimentacao.values()) {
			if (tipoMovimentacao2.getDescricao().equals(tipo.toUpperCase())) {
				tipoMovimentacao =  tipoMovimentacao2;
			}
		}
		return tipoMovimentacao;
	}
	
	
	private TipoMovimentacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
