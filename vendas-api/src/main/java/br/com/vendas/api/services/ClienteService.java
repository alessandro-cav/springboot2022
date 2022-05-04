package br.com.vendas.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.Cliente;
import br.com.vendas.api.entities.Conta;
import br.com.vendas.api.entities.Endereco;
import br.com.vendas.api.entities.Movimentacao;
import br.com.vendas.api.entities.Pedido;
import br.com.vendas.api.handlers.BadRequestException;
import br.com.vendas.api.handlers.ClienteNotFoundException;
import br.com.vendas.api.handlers.ContaNotFoundException;
import br.com.vendas.api.handlers.EnderecoNotFoundException;
import br.com.vendas.api.handlers.MovimentacaoNotFoundException;
import br.com.vendas.api.handlers.PedidoNotFoundException;
import br.com.vendas.api.repositories.ClienteRepository;
import br.com.vendas.api.requests.ClienteRequest;
import br.com.vendas.api.responses.ClienteResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository repository;

	public ClienteResponse save(ClienteRequest clienteRequest) {

		this.checkIfCpfAlreadyExists(clienteRequest.getCpf());
		this.checkIfEmailAlreadyExists(clienteRequest.getEmail());
		this.checkIfPhoneAlreadyExists(clienteRequest.getTelefone());

		Cliente cliente = this.createCustomer(clienteRequest);
		cliente = this.repository.save(cliente);
		return this.createCustomerResponse(cliente);
	}

	public List<ClienteResponse> findAll(Pageable pageable) {
		List<Cliente> clientes = this.repository.findAll(pageable).toList();
		this.checkIfTheListIsEmpty(clientes);
		return clientes.stream().map(cliente -> this.createCustomerResponse(cliente))
				.collect(Collectors.toList());
	}

	public ClienteResponse findClieneByCpf(String cpf) {
		Cliente cliente = this.getCustomerByCpf(cpf);
		return this.createCustomerResponse(cliente);
	}

	public ClienteResponse update(ClienteRequest clienteRequest) {
		Cliente cliente = this.getCustomerByCpf(clienteRequest.getCpf());
		this.checkIfExistEmailandPhone(cliente, clienteRequest.getEmail(), clienteRequest.getTelefone());

		cliente.setNome(clienteRequest.getNome());
		cliente.setEmail(clienteRequest.getEmail());
		cliente.setTelefone(clienteRequest.getTelefone());
		cliente.setCpf(clienteRequest.getCpf());

		cliente = this.repository.save(cliente);
		return this.createCustomerResponse(cliente);
	}

	public void delete(String cpf) {
		Cliente cliente = this.getCustomerByCpf(cpf);
		this.repository.delete(cliente);
	}

	public Cliente getCustomerByCpf(String cpf) {
		return this.repository.findByCpf(cpf).orElseThrow(() -> {
			throw new ClienteNotFoundException("Cliente não encontrado com esse cpf: " + cpf);
		});
	}
	
	public List<Endereco> getAddressListByCustomerId(Long idCliente) {
		List<Endereco> enderecos = this.repository.getAddressListByCustomerId(idCliente);
		if (enderecos.isEmpty()) {
			throw new EnderecoNotFoundException("Lista de endereco está vazia");
		}
		return enderecos;
	}
	
	public Endereco searchAddressByCpfAnCodeAddress(String cpf, String codigo) {
		Endereco endereco = this.repository.searchAddressByCpfAnCodeAddress(cpf, codigo);
		if (endereco == null) {
			throw new EnderecoNotFoundException(
					"Endereço não encontrado com esse cpf: " + cpf + " e codigo: " + codigo);
		}
		return endereco;
	}
	
	public List<Pedido> getDemandListByCustomerId(Long idCliente) {
		List<Pedido> pedidos = this.repository.getRequestListByCustomerId(idCliente);
		if (pedidos.isEmpty()) {
			throw new PedidoNotFoundException("Lista de pedido esta vazia.");
		}
		return pedidos;
	}

	public Pedido searchDemandByCpfAnNumberDemand(String cpf, String numero) {
		Pedido pedido = this.repository.searchDemandByCpfAnNumberDemand(cpf, numero);
		if (pedido == null) {
			throw new PedidoNotFoundException("Pedido não encontrado com esse cpf: " + cpf + " e numero " + numero);
		}
		return pedido;
	}
	
	public List<Conta> fetchAccountListByCustomerId(Long idCliente) {
		List<Conta> contas = this.repository.fetchAccountListByCustomerId(idCliente);
		if (contas.isEmpty()) {
			throw new ContaNotFoundException("Lista de conta esta vazia.");
		}
		return contas;
	}
	
	public Conta searchAccountByCustomerCpfAndAccountNumber(String cpf, String numero) {
		Conta conta = this.repository.searchAccountByCustomerIdAndAccountId(cpf, numero);
		if (conta == null) {
			throw new ContaNotFoundException("Conta não encontrado com esse cpf: " + cpf + " e numero " + numero);
		}
		return conta;
	}

	public List<Movimentacao> getTransactionListByIdClientAndIdAccount(Long idCliente, Long idConta) {
		List<Movimentacao> movimetacoes = this.repository.getTransactionListByIdClientAndIdAccount(idCliente, idConta);
		if (movimetacoes.isEmpty()) {
			throw new MovimentacaoNotFoundException("Lista de movimentações está vazia!");
		}
		return movimetacoes;
	}
	
	private void checkIfExistEmailandPhone(Cliente cliente, String email, String telefone) {

		if (!cliente.getEmail().equals(email)) {
			this.checkIfEmailAlreadyExists(email);
		}

		if (!cliente.getTelefone().equals(telefone)) {
			this.checkIfPhoneAlreadyExists(telefone);
		}
	}

	private void checkIfTheListIsEmpty(List<Cliente> clientes) {
		if (clientes.isEmpty())
			throw new ClienteNotFoundException("Lista de clientes esta vazia");
	}

	private ClienteResponse createCustomerResponse(Cliente cliente) {
		return ClienteResponse.builder().idCliente(cliente.getIdCliente()).nome(cliente.getNome())
				.email(cliente.getEmail()).telefone(cliente.getTelefone()).dataCadastro(cliente.getDataCadastro())
				.cpf(cliente.getCpf()).build();
	}

	private Cliente createCustomer(ClienteRequest clienteRequest) {
		return Cliente.builder().email(clienteRequest.getEmail()).nome(clienteRequest.getNome())
				.cpf(clienteRequest.getCpf()).telefone(clienteRequest.getTelefone()).build();
	}

	private void checkIfCpfAlreadyExists(String cpf) {
		this.repository.findByCpf(cpf).ifPresent(cliente -> {
			throw new BadRequestException("CPF já cadastrado");
		});
	}

	private void checkIfPhoneAlreadyExists(String telefone) {
		this.repository.findByTelefone(telefone).ifPresent(cliente -> {
			throw new BadRequestException("Telefone já cadastrado");
		});
	}

	private void checkIfEmailAlreadyExists(String email) {
		this.repository.findByEmail(email).ifPresent(cliente -> {
			throw new BadRequestException("Email já cadastrado");
		});
	}

	public Pedido searchRequestByCpfAnNumberDemand(String cpf, String numeroPedido) {
		Pedido pedido = this.repository.searchRequestByCpfAndNumberDemand(cpf, numeroPedido);
		if (pedido == null) {
			throw new PedidoNotFoundException("Pedido não encontrado com esse cpf: " + cpf + " e numero " + numeroPedido);
		}
		return pedido;
	}
}
