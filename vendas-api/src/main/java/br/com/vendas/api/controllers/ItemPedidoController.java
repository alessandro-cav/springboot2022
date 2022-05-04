package br.com.vendas.api.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendas.api.requests.ClientePedidoItemPedidoRequest;
import br.com.vendas.api.requests.ClientePedidoPodutoRequest;
import br.com.vendas.api.requests.ClientePedidoRequest;
import br.com.vendas.api.requests.ItemPedidoRequest;
import br.com.vendas.api.requests.PagamentoRequest;
import br.com.vendas.api.responses.ClientePedidoSituacaoResponse;
import br.com.vendas.api.responses.ItemPedidoResponse;
import br.com.vendas.api.responses.MensagemMovimentacaoResponse;
import br.com.vendas.api.responses.MensagemPedidoSituacaoResponse;
import br.com.vendas.api.services.ItemPedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itemPedidos")
public class ItemPedidoController {

	private final ItemPedidoService service;

	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody ItemPedidoRequest itemPedidoRequest) {
		this.service.save(itemPedidoRequest);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/excluir")
	public ResponseEntity<Void> delete(@Valid @RequestBody ClientePedidoPodutoRequest clientePedidoPodutoRequest) {
		this.service.delete(clientePedidoPodutoRequest);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/finalizar")
	public ResponseEntity<ItemPedidoResponse> finish(
			@Valid @RequestBody ClientePedidoItemPedidoRequest clientePedidoItemPedidoRequest) {
		return ResponseEntity.ok(this.service.finish(clientePedidoItemPedidoRequest));
	}

	@PostMapping("/pagamento")
	public ResponseEntity<MensagemMovimentacaoResponse> payment(@Valid @RequestBody PagamentoRequest pagamentoRequest) {
		return  ResponseEntity.ok(this.service.payment(pagamentoRequest));
	}

	@PostMapping("/preparandoProduto")
	public ResponseEntity<MensagemPedidoSituacaoResponse> preparingProduct(
			@Valid @RequestBody ClientePedidoRequest clientePedidoRequest) {
		return  ResponseEntity.ok(this.service.preparingProduct(clientePedidoRequest));
	}

	@PostMapping("/produtoEmTransporte")
	public ResponseEntity<MensagemPedidoSituacaoResponse> productInTransport(
			@Valid @RequestBody ClientePedidoRequest clientePedidoRequest) {
		return  ResponseEntity.ok(this.service.productInTransport(clientePedidoRequest));
	}

	@PostMapping("/produtoEntregue")
	public ResponseEntity<MensagemPedidoSituacaoResponse> productDelivered(
			@Valid @RequestBody ClientePedidoRequest clientePedidoRequest) {
		return  ResponseEntity.ok(this.service.productDelivered(clientePedidoRequest));
	}
	
	@PostMapping("/statusDoPedido")
	public ResponseEntity<ClientePedidoSituacaoResponse> situationHistory(@Valid @RequestBody ClientePedidoRequest clientePedidoRequest) {
		return  ResponseEntity.ok(this.service.situationHistory(clientePedidoRequest));
	}
}
