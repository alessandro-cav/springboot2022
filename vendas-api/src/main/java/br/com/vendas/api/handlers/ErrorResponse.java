package br.com.vendas.api.handlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse {

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataErro;

	private List<String> erros;

	public ErrorResponse(LocalDateTime dataErro, List<ObjectError> allErrors) {
		this.dataErro = dataErro;
		this.erros = allErrors.stream().map(erro -> erro.getDefaultMessage()).sorted().collect(Collectors.toList());
	}
}

