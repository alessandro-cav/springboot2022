package br.com.vendas.api.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendas.api.requests.ClienteCpfRequest;
import br.com.vendas.api.requests.ClientePedidoRequest;
import br.com.vendas.api.responses.ClientePedidoResponse;
import br.com.vendas.api.services.PedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("clientes/pedidos")
public class ClientePedidoController {
	
	private final PedidoService service;

	@PostMapping
	public ResponseEntity<ClientePedidoResponse> save(@Valid @RequestBody ClientePedidoRequest pedidoRequest) {
		return ResponseEntity.ok(this.service.save(pedidoRequest));
	}

	@PostMapping("/lista")
	public ResponseEntity<ClientePedidoResponse> findAll(@Valid @RequestBody ClienteCpfRequest clienteCpfRequest,
			Pageable pageable) {
		return ResponseEntity.ok(this.service.findAll(clienteCpfRequest.getCpf(), pageable));
	}

	@PostMapping("/buscarPeloNumero")
	public ResponseEntity<ClientePedidoResponse> findByNumber(@Valid @RequestBody ClientePedidoRequest pedidoRequest) {
		return ResponseEntity.ok(this.service.findByNumber(pedidoRequest.getCpf(), pedidoRequest.getNumeroPedido()));
	}

	@PostMapping("/excluir")
	public ResponseEntity<Void> delete(@Valid @RequestBody ClientePedidoRequest pedidoRequest) {
		this.service.delete(pedidoRequest.getCpf(), pedidoRequest.getNumeroPedido());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/atualizar")
	public ResponseEntity<ClientePedidoResponse> update(@Valid @RequestBody ClientePedidoRequest pedidoRequest) {
		return ResponseEntity.ok(this.service.update(pedidoRequest));
	}
}
