package br.com.vendas.api.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendas.api.requests.ClienteCpfRequest;
import br.com.vendas.api.requests.ClienteEnderecoRequest;
import br.com.vendas.api.requests.EnderecoRequest;
import br.com.vendas.api.responses.ClienteEnderecoResponse;
import br.com.vendas.api.services.EnderecoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("clientes/enderecos")
public class ClienteEnderecoController {

	private final EnderecoService service;

	@PostMapping
	public ResponseEntity<ClienteEnderecoResponse> save(@Valid @RequestBody EnderecoRequest enderecoRequest) {
		return ResponseEntity.ok(this.service.save(enderecoRequest));
	}

	@PostMapping("/lista")
	public ResponseEntity<ClienteEnderecoResponse> findAll(@Valid @RequestBody ClienteCpfRequest clienteCpfRequest,
			Pageable pageable) {
		return ResponseEntity.ok(this.service.findAll(clienteCpfRequest.getCpf(), pageable));
	}

	@PostMapping("/buscarPeloCodigo")
	public ResponseEntity<ClienteEnderecoResponse> searchAddressByCpfAnCodeAddress(
			@Valid @RequestBody ClienteEnderecoRequest enderecoCodigoCpfRequest) {
		return ResponseEntity.ok(this.service.searchAddressByCpfAnCodeAddress(enderecoCodigoCpfRequest.getCpf(),
				enderecoCodigoCpfRequest.getCodigo()));
	}

	@PostMapping("/excluir")
	public ResponseEntity<Void> delete(@Valid @RequestBody ClienteEnderecoRequest enderecoCodigoCpfRequest) {
		this.service.delete(enderecoCodigoCpfRequest.getCpf(), enderecoCodigoCpfRequest.getCodigo());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/atualizar")
	public ResponseEntity<ClienteEnderecoResponse> update(@Valid @RequestBody EnderecoRequest enderecoRequest) {
		return ResponseEntity.ok(this.service.update(enderecoRequest));
	}
}
