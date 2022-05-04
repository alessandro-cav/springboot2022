package br.com.vendas.api.handlers;

public class ContaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
		
	public ContaNotFoundException(String message) {
		super(message);
	}

}

