package br.com.vendas.api.handlers;

public class EnderecoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EnderecoNotFoundException(String message) {
		super(message);
	}

}