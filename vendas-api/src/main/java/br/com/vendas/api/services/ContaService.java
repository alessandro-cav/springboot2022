package br.com.vendas.api.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.Cliente;
import br.com.vendas.api.entities.Conta;
import br.com.vendas.api.entities.Movimentacao;
import br.com.vendas.api.enuns.TipoConta;
import br.com.vendas.api.enuns.TipoMovimentacao;
import br.com.vendas.api.handlers.BadRequestException;
import br.com.vendas.api.repositories.ContaRepository;
import br.com.vendas.api.requests.ContaMovimentacaoRequest;
import br.com.vendas.api.requests.ContaRequest;
import br.com.vendas.api.requests.TransferenciaResquet;
import br.com.vendas.api.responses.ClienteContaResponse;
import br.com.vendas.api.responses.ContaResponse;
import br.com.vendas.api.responses.MensagemMovimentacaoResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaService {

	private final ContaRepository repository;

	private final ClienteService clienteService;

	private final MovimentacaoService movimentacaoService;

	public ClienteContaResponse save(ContaRequest contaRequest) {
		Cliente cliente = this.clienteService.getCustomerByCpf(contaRequest.getCpf());
		this.checkIfAccountNumberAlreadyExists(contaRequest.getNumero());

		Conta conta = this.createAccount(cliente, contaRequest);
		conta = this.repository.save(conta);

		ContaResponse contaResponse = this.createAccountResponse(conta);
		return this.createCustomerAccountResponse(cliente, Arrays.asList(contaResponse));
	}

	public ClienteContaResponse findAll(String cpf, Pageable pageable) {
		Cliente cliente = this.clienteService.getCustomerByCpf(cpf);
		List<Conta> contas = this.clienteService.fetchAccountListByCustomerId(cliente.getIdCliente());
		List<ContaResponse> contaResponses = contas.stream().map(conta -> this.createAccountResponse(conta))
				.collect(Collectors.toList());
		return this.createCustomerAccountResponse(cliente, contaResponses);
	}

	public ClienteContaResponse findByNumberAndCpf(String cpf, String numero) {
		Conta conta = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(cpf, numero);
		ContaResponse contaResponse = this.createAccountResponse(conta);
		return this.createCustomerAccountResponse(conta.getCliente(), Arrays.asList(contaResponse));
	}

	public void delete(String cpf, String numero) {
		Conta conta = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(cpf, numero);
		this.repository.delete(conta);
	}

	public ClienteContaResponse update(ContaRequest contaRequest) {
		Conta conta = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(contaRequest.getCpf(),
				contaRequest.getNumero());

		conta.setLimite(contaRequest.getLimite());
		conta.setSaldo(contaRequest.getSaldo());
		conta.setTipoConta(TipoConta.getAccountType(contaRequest.getTipoConta()));

		conta = this.repository.save(conta);
		ContaResponse contaResponse = this.createAccountResponse(conta);
		return this.createCustomerAccountResponse(conta.getCliente(), Arrays.asList(contaResponse));
	}

	public MensagemMovimentacaoResponse withdraw(ContaMovimentacaoRequest contaMovimentacaoRequest) {
		Conta conta = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(contaMovimentacaoRequest.getCpf(),
				contaMovimentacaoRequest.getNumero());

		if (conta.getSaldo().compareTo(contaMovimentacaoRequest.getValor()) >= 0) {
			conta.setSaldo(conta.getSaldo().subtract(contaMovimentacaoRequest.getValor()));
		} else {
			if (conta.getSaldo().add(conta.getLimite()).compareTo(contaMovimentacaoRequest.getValor()) >= 0) {
				BigDecimal novoValor = contaMovimentacaoRequest.getValor().subtract(conta.getSaldo());
				conta.setLimite(conta.getLimite().subtract(novoValor));
				conta.setSaldo(BigDecimal.ZERO);
			} else {
				throw new BadRequestException(
						"Valor " + contaMovimentacaoRequest.getValor() + " reais não disponivel para saque.");
			}
		}

		Movimentacao movimentacao = this.movimentacaoService.createMovement(contaMovimentacaoRequest.getValor(), conta,
				TipoMovimentacao.SAQUE);
		this.movimentacaoService.save(movimentacao);
		this.repository.save(conta);
		return this.AccountMovementResponse(movimentacao.getTipoMovimentacao());
	}

	public MensagemMovimentacaoResponse deposit(
			@Valid ContaMovimentacaoRequest contaMovimentacaoRequest) {
		Conta conta = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(contaMovimentacaoRequest.getCpf(),
				contaMovimentacaoRequest.getNumero());

		conta.setSaldo(conta.getSaldo().add(contaMovimentacaoRequest.getValor()));

		Movimentacao movimentacao = this.movimentacaoService.createMovement(contaMovimentacaoRequest.getValor(), conta,
				TipoMovimentacao.DEPOSITO);
		this.movimentacaoService.save(movimentacao);
		this.repository.save(conta);
		return this.AccountMovementResponse(movimentacao.getTipoMovimentacao());
	}

	public MensagemMovimentacaoResponse transfer(TransferenciaResquet transferenciaRequest) {
		Conta contaOrigem = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(
				transferenciaRequest.getClienteCpfOrigem(), transferenciaRequest.getContaNumeroOrigem());

		Conta contaDestino = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(
				transferenciaRequest.getClienteCpfDestino(), transferenciaRequest.getContaNumeroDestino());

		if (contaOrigem.getSaldo().compareTo(transferenciaRequest.getValor()) >= 0) {
			contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(transferenciaRequest.getValor()));
		} else {
			if (contaOrigem.getSaldo().add(contaOrigem.getLimite()).compareTo(transferenciaRequest.getValor()) >= 0) {
				BigDecimal novoValor = transferenciaRequest.getValor().subtract(contaOrigem.getSaldo());
				contaOrigem.setLimite(contaOrigem.getLimite().subtract(novoValor));
				contaOrigem.setSaldo(BigDecimal.ZERO);
				;
			} else {
				throw new BadRequestException(
						"Valor " + transferenciaRequest.getValor() + " reais não disponivel para transferência.");
			}
		}

		contaDestino.setSaldo(contaDestino.getSaldo().add(transferenciaRequest.getValor()));

		Movimentacao movimentacaoOrigem = this.movimentacaoService.createMovement(transferenciaRequest.getValor(),
				contaOrigem, TipoMovimentacao.TRANSFERENCIA);
		Movimentacao movimentacaoDestino = this.movimentacaoService.createMovement(transferenciaRequest.getValor(),
				contaDestino, TipoMovimentacao.TRANSFERENCIA);

		this.movimentacaoService.save(movimentacaoOrigem);
		this.movimentacaoService.save(movimentacaoDestino);
		this.repository.save(contaDestino);
		this.repository.save(contaOrigem);

		return this.AccountMovementResponse(movimentacaoOrigem.getTipoMovimentacao());
	}

	public MensagemMovimentacaoResponse AccountMovementResponse(TipoMovimentacao tipoMovimentacao) {
		String menssagem = tipoMovimentacao + " com sucesso!!";
		return MensagemMovimentacaoResponse.builder().messagem(menssagem).build();
	}

	private ClienteContaResponse createCustomerAccountResponse(Cliente cliente, List<ContaResponse> contaResponses) {
		return ClienteContaResponse.builder().idCliente(cliente.getIdCliente()).nome(cliente.getNome())
				.cpf(cliente.getCpf()).email(cliente.getEmail()).dataCadastro(cliente.getDataCadastro())
				.telefone(cliente.getTelefone()).contas(contaResponses).build();
	}

	private ContaResponse createAccountResponse(Conta conta) {
		return ContaResponse.builder().idConta(conta.getIdConta()).numero(conta.getNumeroConta())
				.limite(conta.getLimite()).saldo(conta.getSaldo()).tipoConta(conta.getTipoConta()).build();
	}

	private Conta createAccount(Cliente cliente, ContaRequest contaRequest) {
		return Conta.builder().limite(contaRequest.getLimite()).numeroConta(contaRequest.getNumero())
				.saldo(contaRequest.getSaldo()).tipoConta(TipoConta.getAccountType(contaRequest.getTipoConta()))
				.cliente(cliente).build();
	}

	private void checkIfAccountNumberAlreadyExists(String numero) {
		this.repository.findByNumeroConta(numero).ifPresent(conta -> {
			throw new BadRequestException("Numero da conta já cadastrado");
		});
	}

	public void saveAccount(Conta conta) {
		this.repository.save(conta);

	}
}
