package br.com.vendas.api.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendas.api.requests.ClienteContaRequest;
import br.com.vendas.api.requests.ClienteCpfRequest;
import br.com.vendas.api.requests.ContaMovimentacaoRequest;
import br.com.vendas.api.requests.ContaRequest;
import br.com.vendas.api.requests.TransferenciaResquet;
import br.com.vendas.api.responses.ClienteContaResponse;
import br.com.vendas.api.responses.MensagemMovimentacaoResponse;
import br.com.vendas.api.services.ContaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("clientes/contas")
public class ClienteContaController {

	private final ContaService service;

	@PostMapping
	public ResponseEntity<ClienteContaResponse> save(@Valid @RequestBody ContaRequest contaRequest) {
		return ResponseEntity.ok(this.service.save(contaRequest));
	}

	@PostMapping("/lista")
	public ResponseEntity<ClienteContaResponse> findAll(@Valid @RequestBody ClienteCpfRequest clienteCpfRequest,
			Pageable pageable) {
		return ResponseEntity.ok(this.service.findAll(clienteCpfRequest.getCpf(), pageable));
	}

	@PostMapping("/buscarPeloNumero")
	public ResponseEntity<ClienteContaResponse> findByNumberAndCpf(
			@Valid @RequestBody ClienteContaRequest clienteContaRequest) {
		return ResponseEntity.ok(this.service.findByNumberAndCpf(clienteContaRequest.getCpf(), clienteContaRequest.getNumero()));
	}

	@PostMapping("/excluir")
	public ResponseEntity<Void> delete(@Valid @RequestBody ClienteContaRequest clienteContaRequest) {
		this.service.delete(clienteContaRequest.getCpf(), clienteContaRequest.getNumero());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/atualizar")
	public ResponseEntity<ClienteContaResponse> update(@Valid @RequestBody ContaRequest contaRequest) {
		return ResponseEntity.ok(this.service.update(contaRequest));
	}

	@PostMapping("/saque")
	public ResponseEntity<MensagemMovimentacaoResponse> withdraw(
			@RequestBody @Valid ContaMovimentacaoRequest contaMovimentacaoRequest) {
		return ResponseEntity.ok(this.service.withdraw(contaMovimentacaoRequest));
	}

	@PostMapping("/deposito")
	public ResponseEntity<MensagemMovimentacaoResponse> deposit(
			@RequestBody @Valid ContaMovimentacaoRequest contaMovimentacaoRequest) {
		return ResponseEntity.ok(this.service.deposit(contaMovimentacaoRequest));
	}

	@PostMapping("/transferencia")
	public ResponseEntity<MensagemMovimentacaoResponse> transfer(
			@RequestBody @Valid TransferenciaResquet transferenciaRequest) {
		return ResponseEntity.ok(this.service.transfer(transferenciaRequest));
	}
}
