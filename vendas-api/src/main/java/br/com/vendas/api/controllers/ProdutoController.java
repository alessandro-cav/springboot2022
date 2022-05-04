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

import br.com.vendas.api.requests.ProdutoCodigoRequest;
import br.com.vendas.api.requests.ProdutoRequest;
import br.com.vendas.api.responses.ProdutoResponse;
import br.com.vendas.api.services.ProdutoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService service;

	@PostMapping
	public ResponseEntity<ProdutoResponse> save(@Valid @RequestBody ProdutoRequest produtoRequest) {
		return ResponseEntity.ok(this.service.save(produtoRequest));
	}

	@PostMapping("/buscaPeloCodigo")
	public ResponseEntity<ProdutoResponse> findByCodigo(@Valid @RequestBody ProdutoCodigoRequest codigoRequest) {
		return ResponseEntity.ok(this.service.findByCodigo(codigoRequest.getCodigo()));
	}

	@GetMapping
	public ResponseEntity<List<ProdutoResponse>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.service.findAll(pageable));
	}

	@PostMapping("/excluir")
	public ResponseEntity<Void> delete(@Valid @RequestBody ProdutoCodigoRequest codigoRequest) {
		this.service.delete(codigoRequest.getCodigo());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/atualizar")
	public ResponseEntity<ProdutoResponse> update(@Valid @RequestBody ProdutoRequest produtoRequest) {
		return ResponseEntity.ok(this.service.update(produtoRequest));
	}
}
