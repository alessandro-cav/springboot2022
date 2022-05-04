package br.com.vendas.api.handlers;

public class PedidoSituacaoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PedidoSituacaoNotFoundException(String message) {
		super(message);
	}

}
