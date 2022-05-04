package br.com.vendas.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendas.api.requests.ClienteCpfRequest;
import br.com.vendas.api.requests.ClienteRequest;
import br.com.vendas.api.responses.ClienteResponse;
import br.com.vendas.api.services.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteService service;
	
	@PostMapping
	public ResponseEntity<ClienteResponse> save(@Valid @RequestBody ClienteRequest clienteRequest) {
		return ResponseEntity.ok(this.service.save(clienteRequest));
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteResponse>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.service.findAll(pageable));
	}
	
	@PostMapping("/buscarPeloCPF")
	public ResponseEntity<ClienteResponse> findClienteByCpf(@Valid @RequestBody ClienteCpfRequest cpfRequest ) {
		return ResponseEntity.ok(this.service.findClieneByCpf(cpfRequest.getCpf()));
	}
	
	@PostMapping("/atualizar")
	public ResponseEntity<ClienteResponse> update(@Valid @RequestBody ClienteRequest clienteRequest) {
		return ResponseEntity.ok(this.service.update(clienteRequest));
	}
	
	@PostMapping("/excluir")
	public ResponseEntity<Void> delete(@Valid @RequestBody ClienteCpfRequest cpfRequest) {
		this.service.delete(cpfRequest.getCpf());
		return ResponseEntity.noContent().build();
	}
}
