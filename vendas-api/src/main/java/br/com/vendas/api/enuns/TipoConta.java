package br.com.vendas.api.enuns;

public enum TipoConta {

	CONTA_CORRETENTE("CORRENTE"), CONTA_POUPANCA("POUPANCA");

	private String descricao;

	public static TipoConta getAccountType(String tipo) {
		TipoConta tipoConta2 = null;
		for (TipoConta tipoConta : TipoConta.values()) {
			if (tipoConta.getDescricao().equals(tipo.toUpperCase())) {
				 tipoConta2 =  tipoConta;
			}
		}
		return tipoConta2;
	}
	
	private TipoConta(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}

