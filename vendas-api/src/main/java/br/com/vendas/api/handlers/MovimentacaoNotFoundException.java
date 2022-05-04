package br.com.vendas.api.handlers;

public class MovimentacaoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MovimentacaoNotFoundException(String messge) {
		super(messge);
	}
}

