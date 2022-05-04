package br.com.vendas.api.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendas.api.requests.ClienteContaRequest;
import br.com.vendas.api.responses.ClienteContaMovimentacaoResponse;
import br.com.vendas.api.services.MovimentacaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("clientes/contas/movimentacoes")
public class ClienteContaMovimentacaoController {

	private final MovimentacaoService service;

	@PostMapping("/extratos")
	public ResponseEntity<ClienteContaMovimentacaoResponse> getMovementList(@Valid @RequestBody ClienteContaRequest clienteContaRequest) {
		return ResponseEntity.ok(this.service.getMovementList(clienteContaRequest));
	}
}
