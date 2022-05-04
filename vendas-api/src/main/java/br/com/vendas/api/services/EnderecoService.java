package br.com.vendas.api.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.Cliente;
import br.com.vendas.api.entities.Endereco;
import br.com.vendas.api.handlers.BadRequestException;
import br.com.vendas.api.repositories.EnderecoRepository;
import br.com.vendas.api.requests.EnderecoRequest;
import br.com.vendas.api.responses.ClienteEnderecoResponse;
import br.com.vendas.api.responses.EnderecoResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnderecoService {

	private final EnderecoRepository repository;

	private final ClienteService clienteService;

	public ClienteEnderecoResponse save(EnderecoRequest enderecoRequest) {
		Cliente cliente = this.clienteService.getCustomerByCpf(enderecoRequest.getCpf());
		this.checkICodeAlreadyExists(enderecoRequest.getCodigo());

		Endereco endereco = this.createAddress(cliente, enderecoRequest);
		endereco = this.repository.save(endereco);

		EnderecoResponse enderecoResponse = this.createAddressResponse(endereco);
		return this.createCustomerAddressResponse(cliente,
				Arrays.asList(enderecoResponse));
	}

	public ClienteEnderecoResponse findAll(String cpf, Pageable pageable) {
		Cliente cliente = this.clienteService.getCustomerByCpf(cpf);
		List<Endereco> enderecos = this.clienteService.getAddressListByCustomerId(cliente.getIdCliente());
		List<EnderecoResponse> enderecoResponses = enderecos.stream()
				.map(endereco -> this.createAddressResponse(endereco)).collect(Collectors.toList());
		return this.createCustomerAddressResponse(cliente, enderecoResponses);
	}

	public ClienteEnderecoResponse searchAddressByCpfAnCodeAddress(String cpf, String codigo) {
		Endereco endereco = this.clienteService.searchAddressByCpfAnCodeAddress(cpf, codigo);
		EnderecoResponse enderecoResponse = this.createAddressResponse(endereco);
		return this.createCustomerAddressResponse(endereco.getCliente(),
				Arrays.asList(enderecoResponse));
	}

	public void delete(String cpf, String codigo) {
		Endereco endereco = this.clienteService.searchAddressByCpfAnCodeAddress(cpf, codigo);
		this.repository.delete(endereco);
	}

	public ClienteEnderecoResponse update(EnderecoRequest enderecoRequest) {
		Endereco endereco = this.clienteService.searchAddressByCpfAnCodeAddress(enderecoRequest.getCpf(),
				enderecoRequest.getCodigo());
		
		endereco.setBairro(enderecoRequest.getBairro());
		endereco.setCidade(enderecoRequest.getCidade());
		endereco.setComplemento(enderecoRequest.getComplemento());
		endereco.setLogradouro(enderecoRequest.getLogradouro());
		endereco.setNumero(enderecoRequest.getNumero());
		endereco.setCodigoEndereco(enderecoRequest.getCodigo());

		endereco = this.repository.save(endereco);
		EnderecoResponse enderecoResponse = this.createAddressResponse(endereco);
		return this.createCustomerAddressResponse(endereco.getCliente(),
				Arrays.asList(enderecoResponse));
	}

	private ClienteEnderecoResponse createCustomerAddressResponse(Cliente cliente,
			List<EnderecoResponse> enderecoResponse) {
		return ClienteEnderecoResponse.builder().idCliente(cliente.getIdCliente()).cpf(cliente.getCpf())
				.dataCadastro(cliente.getDataCadastro()).email(cliente.getEmail()).enderecos(enderecoResponse)
				.nome(cliente.getNome()).telefone(cliente.getTelefone()).build();
	}

	private EnderecoResponse createAddressResponse(Endereco endereco) {
		return EnderecoResponse.builder().idEndereco(endereco.getIdEndereco()).codigo(endereco.getCodigoEndereco())
				.bairro(endereco.getBairro()).cidade(endereco.getCidade()).complemento(endereco.getComplemento())
				.logradouro(endereco.getLogradouro()).numero(endereco.getNumero()).build();
	}

	private Endereco createAddress(Cliente cliente, EnderecoRequest enderecoRequest) {
		return Endereco.builder().codigoEndereco(enderecoRequest.getCodigo()).bairro(enderecoRequest.getBairro())
				.cidade(enderecoRequest.getCidade()).complemento(enderecoRequest.getComplemento())
				.logradouro(enderecoRequest.getLogradouro()).numero(enderecoRequest.getNumero()).cliente(cliente)
				.build();
	}

	private void checkICodeAlreadyExists(String codigo) {
		this.repository.findByCodigoEndereco(codigo).ifPresent(endereco -> {
			throw new BadRequestException("Codigo já cadastrado");
		});
	}
}

