package br.com.vendas.api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.Cliente;
import br.com.vendas.api.entities.Conta;
import br.com.vendas.api.entities.ItemPedido;
import br.com.vendas.api.entities.Movimentacao;
import br.com.vendas.api.entities.Pedido;
import br.com.vendas.api.entities.PedidoSituacao;
import br.com.vendas.api.entities.Produto;
import br.com.vendas.api.enuns.SituacaoPedido;
import br.com.vendas.api.enuns.TipoMovimentacao;
import br.com.vendas.api.handlers.BadRequestException;
import br.com.vendas.api.repositories.ItemPedidoRepository;
import br.com.vendas.api.requests.ClientePedidoItemPedidoRequest;
import br.com.vendas.api.requests.ClientePedidoPodutoRequest;
import br.com.vendas.api.requests.ClientePedidoRequest;
import br.com.vendas.api.requests.ItemPedidoRequest;
import br.com.vendas.api.requests.PagamentoRequest;
import br.com.vendas.api.responses.ClientePedidoSituacaoResponse;
import br.com.vendas.api.responses.ItemPedidoResponse;
import br.com.vendas.api.responses.MensagemMovimentacaoResponse;
import br.com.vendas.api.responses.MensagemPedidoSituacaoResponse;
import br.com.vendas.api.responses.PedidoSituacaoResponse;
import br.com.vendas.api.responses.ProdutoResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemPedidoService {

	private final ItemPedidoRepository repository;

	private final ClienteService clienteService;

	private final ContaService contaService;

	private final ProdutoService produtoService;

	private final MovimentacaoService movimentacaoService;

	private final PedidoSituacaoService situacaoService;

	public void save(ItemPedidoRequest itemPedidoRequest) {

		Cliente cliente = this.clienteService.getCustomerByCpf(itemPedidoRequest.getCpf());
		Pedido pedido = this.clienteService.searchRequestByCpfAnNumberDemand(cliente.getCpf(),
				itemPedidoRequest.getNumeroPedido());
		Produto produto = this.produtoService.findProductByCode(itemPedidoRequest.getCodigoProduto());

		ItemPedido itemPedido = this.repository.getItemRequestByOrderNumberAndProductCode(pedido.getIdPedido(),
				produto.getIdProduto());

		if (itemPedido != null) {
			int quantidade = itemPedido.getQuantidade() + itemPedidoRequest.getQuantidade();
			itemPedido.setQuantidade(quantidade);
			BigDecimal precoQtd = produto.getPreco().multiply(new BigDecimal(quantidade));

			itemPedido.setTotal(precoQtd);
			itemPedido = this.repository.save(itemPedido);
		} else {
			itemPedido = new ItemPedido();
			itemPedido.setNumeroItemPedido(itemPedidoRequest.getNumeroItemPedido());
			itemPedido.setProduto(produto);
			itemPedido.setPedido(pedido);
			itemPedido.setQuantidade(itemPedidoRequest.getQuantidade());
			BigDecimal precoQtd = produto.getPreco().multiply(new BigDecimal(itemPedidoRequest.getQuantidade()));

			itemPedido.setTotal(precoQtd);
			itemPedido = this.repository.save(itemPedido);
		}
	}

	public void delete(ClientePedidoPodutoRequest clientePedidoPodutoRequest) {
		Cliente cliente = this.clienteService.getCustomerByCpf(clientePedidoPodutoRequest.getCpf());
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(cliente.getCpf(),
				clientePedidoPodutoRequest.getNumeroPedido());
		Produto produto = this.produtoService.findProductByCode(clientePedidoPodutoRequest.getCodigoProduto());

		ItemPedido itemPedido = this.repository.getItemRequestByOrderNumberAndProductCode(pedido.getIdPedido(),
				produto.getIdProduto());
		this.repository.delete(itemPedido);
	}

	public ItemPedidoResponse finish(ClientePedidoItemPedidoRequest clientePedidoItemPedidoRequest) {
		Cliente cliente = this.clienteService.getCustomerByCpf(clientePedidoItemPedidoRequest.getCpf());
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(cliente.getCpf(),
				clientePedidoItemPedidoRequest.getNumeroPedido());
		List<Produto> produtos = this.produtoService
				.findAllProductsByIdItemDemand(clientePedidoItemPedidoRequest.getNumeroItemPedido());

		List<ProdutoResponse> produtoResponses = produtos.stream()
				.map(produto -> this.produtoService.createProductResponse(produto)).collect(Collectors.toList());

		PedidoSituacao pedidoSituacao = PedidoSituacao.builder().cliente(cliente).pedido(pedido)
				.situacaoPedido(SituacaoPedido.PEDIDO_RECEBIDO).build();
		this.situacaoService.save(pedidoSituacao);

		return ItemPedidoResponse.builder().idCliente(cliente.getIdCliente()).nome(cliente.getNome())
				.email(cliente.getEmail()).telefone(cliente.getTelefone()).dataCadastro(cliente.getDataCadastro())
				.cpf(cliente.getCpf()).idPedido(pedido.getIdPedido()).dataPedido(pedido.getDataPedido())
				.numero(pedido.getNumeroPedido()).produtos(produtoResponses).build();
	}

	public MensagemMovimentacaoResponse payment(PagamentoRequest pagamentoRequest) {

		Conta conta = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(pagamentoRequest.getCpf(),
				pagamentoRequest.getNumeroConta());
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(conta.getCliente().getCpf(),
				pagamentoRequest.getNumeroPedido());

		if (conta.getSaldo().compareTo(pagamentoRequest.getValor()) >= 0) {
			conta.setSaldo(conta.getSaldo().subtract(pagamentoRequest.getValor()));
		} else {
			if (conta.getSaldo().add(conta.getLimite()).compareTo(pagamentoRequest.getValor()) >= 0) {
				BigDecimal novoValor = pagamentoRequest.getValor().subtract(conta.getSaldo());
				conta.setLimite(conta.getLimite().subtract(novoValor));
				conta.setSaldo(BigDecimal.ZERO);
			} else {
				throw new BadRequestException(
						"Valor " + pagamentoRequest.getValor() + " reais não disponivel para pagamento.");
			}
		}

		PedidoSituacao pedidoSituacao = PedidoSituacao.builder().cliente(conta.getCliente()).pedido(pedido)
				.situacaoPedido(SituacaoPedido.PAGAMENTO_CONFIRMADO).build();
		this.situacaoService.save(pedidoSituacao);

		Movimentacao movimentacao = this.movimentacaoService.createMovement(pagamentoRequest.getValor(), conta,
				TipoMovimentacao.PAGAMENTO);
		this.movimentacaoService.save(movimentacao);
		this.contaService.saveAccount(conta);
		return this.contaService.AccountMovementResponse(movimentacao.getTipoMovimentacao());

	}

	public MensagemPedidoSituacaoResponse preparingProduct(ClientePedidoRequest clientePedidoRequest) {
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(clientePedidoRequest.getCpf(),
				clientePedidoRequest.getNumeroPedido());

		PedidoSituacao pedidoSituacao = PedidoSituacao.builder().cliente(pedido.getCliente()).pedido(pedido)
				.situacaoPedido(SituacaoPedido.PREPARANDO_PRODUTO).build();
		pedidoSituacao = this.situacaoService.save(pedidoSituacao);
		return this.demandSituationResponse(pedidoSituacao);

	}

	public MensagemPedidoSituacaoResponse productInTransport(
			ClientePedidoRequest clientePedidoRequest) {
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(clientePedidoRequest.getCpf(),
				clientePedidoRequest.getNumeroPedido());

		PedidoSituacao pedidoSituacao = PedidoSituacao.builder().cliente(pedido.getCliente()).pedido(pedido)
				.situacaoPedido(SituacaoPedido.PRODUTO_EM_TRANSPORTE).build();
		pedidoSituacao = this.situacaoService.save(pedidoSituacao);
		return this.demandSituationResponse(pedidoSituacao);
	}

	public MensagemPedidoSituacaoResponse productDelivered(ClientePedidoRequest clientePedidoRequest) {
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(clientePedidoRequest.getCpf(),
				clientePedidoRequest.getNumeroPedido());

		PedidoSituacao pedidoSituacao = PedidoSituacao.builder().cliente(pedido.getCliente()).pedido(pedido)
				.situacaoPedido(SituacaoPedido.PRODUTO_EM_TRANSPORTE).build();
		this.situacaoService.save(pedidoSituacao);
		return this.demandSituationResponse(pedidoSituacao);
	}

	public MensagemPedidoSituacaoResponse demandSituationResponse(PedidoSituacao pedidoSituacao) {
		String menssagem = pedidoSituacao.getSituacaoPedido().getDescricao() + " com sucesso!!";
		return MensagemPedidoSituacaoResponse.builder().messagem(menssagem).build();
	}

	public ClientePedidoSituacaoResponse situationHistory(ClientePedidoRequest clientePedidoRequest) {
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(clientePedidoRequest.getCpf(),
				clientePedidoRequest.getNumeroPedido());

		List<PedidoSituacao> pedidoSitucoes = this.situacaoService
				.demandStatusHistory(pedido.getCliente().getIdCliente(), pedido.getIdPedido());

		List<PedidoSituacaoResponse> pedidoSituacaoResponses = pedidoSitucoes.stream()
				.map(pedidoSituacao -> PedidoSituacaoResponse.builder().dataSituacao(pedidoSituacao.getDataSituacao())
						.idSituacaoPedido(pedidoSituacao.getIdSituacaoPedido())
						.situacaoPedido(pedidoSituacao.getSituacaoPedido()).build())
				.collect(Collectors.toList());

		return ClientePedidoSituacaoResponse.builder().cpf(pedido.getCliente().getCpf())
				.idCliente(pedido.getCliente().getIdCliente()).dataCadastro(pedido.getCliente().getDataCadastro())
				.email(pedido.getCliente().getEmail()).nome(pedido.getCliente().getNome())
				.telefone(pedido.getCliente().getTelefone()).idPedido(pedido.getIdPedido()).dataPedido(pedido.getDataPedido())
				.numero(pedido.getNumeroPedido()).statusPedido(pedidoSituacaoResponses).build();
	}

}
