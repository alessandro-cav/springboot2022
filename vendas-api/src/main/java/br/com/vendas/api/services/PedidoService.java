package br.com.vendas.api.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.Cliente;
import br.com.vendas.api.entities.Pedido;
import br.com.vendas.api.handlers.BadRequestException;
import br.com.vendas.api.repositories.PedidoRepository;
import br.com.vendas.api.requests.ClientePedidoRequest;
import br.com.vendas.api.responses.ClientePedidoResponse;
import br.com.vendas.api.responses.PedidoResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

	private final PedidoRepository repository;

	private final ClienteService clienteService;

	public ClientePedidoResponse save(ClientePedidoRequest pedidoRequest) {
		Cliente cliente = this.clienteService.getCustomerByCpf(pedidoRequest.getCpf());
		this.checkINumberAlreadyExists(pedidoRequest.getNumeroPedido());

		Pedido pedido = this.createRequest(cliente, pedidoRequest.getNumeroPedido());
		pedido = this.repository.save(pedido);

		PedidoResponse pedidoResponse = this.createDemandResponse(pedido);
		return this.createCustomerDemandResponse(cliente, Arrays.asList(pedidoResponse));
	}

	public ClientePedidoResponse findAll(String cpf, Pageable pageable) {
		Cliente cliente = this.clienteService.getCustomerByCpf(cpf);
		List<Pedido> pedidos = this.clienteService.getDemandListByCustomerId(cliente.getIdCliente());
		List<PedidoResponse> pedidoResponses = pedidos.stream().map(pedido -> this.createDemandResponse(pedido))
				.collect(Collectors.toList());
		return this.createCustomerDemandResponse(cliente, pedidoResponses);
	}

	public ClientePedidoResponse findByNumber(String cpf, String numero) {
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(cpf, numero);
		PedidoResponse pedidoResponse = this.createDemandResponse(pedido);
		return this.createCustomerDemandResponse(pedido.getCliente(), Arrays.asList(pedidoResponse));
	}

	public void delete(String cpf, String numero) {
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(cpf, numero);
		this.repository.delete(pedido);
	}

	public ClientePedidoResponse update(ClientePedidoRequest pedidoRequest) {
		Pedido pedido = this.clienteService.searchDemandByCpfAnNumberDemand(pedidoRequest.getCpf(),
				pedidoRequest.getNumeroPedido());
		pedido.setNumeroPedido(pedidoRequest.getNumeroPedido()); // verificar se pode trocar o cliente
		pedido = this.repository.save(pedido);
		PedidoResponse pedidoResponse = this.createDemandResponse(pedido);
		return this.createCustomerDemandResponse(pedido.getCliente(), Arrays.asList(pedidoResponse));
	}

	private ClientePedidoResponse createCustomerDemandResponse(Cliente cliente, List<PedidoResponse> pedidoResponses) {
		return ClientePedidoResponse.builder().idCliente(cliente.getIdCliente()).cpfCnpj(cliente.getCpf())
				.dataCadastro(cliente.getDataCadastro()).email(cliente.getEmail()).pedidos(pedidoResponses)
				.nome(cliente.getNome()).telefone(cliente.getTelefone()).build();
	}

	private PedidoResponse createDemandResponse(Pedido pedido) {
		return PedidoResponse.builder().idPedido(pedido.getIdPedido()).dataPedido(pedido.getDataPedido())
				.numero(pedido.getNumeroPedido()).build();
	}

	private Pedido createRequest(Cliente cliente, String numero) {
		return Pedido.builder().cliente(cliente).numeroPedido(numero).build();
	}

	private void checkINumberAlreadyExists(String numero) {
		this.repository.findByNumeroPedido(numero).ifPresent(endereco -> {
			throw new BadRequestException("Numero já cadastrado");
		});
	}
}
