package br.com.vendas.api.responses;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idCliente;
	
	private String nome;
	
	private String cpf;
	
	private String email;
	
	private String telefone;
	
	private LocalDateTime dataCadastro;
}
