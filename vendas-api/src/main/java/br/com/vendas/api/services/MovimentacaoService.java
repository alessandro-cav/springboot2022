package br.com.vendas.api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.Cliente;
import br.com.vendas.api.entities.Conta;
import br.com.vendas.api.entities.Movimentacao;
import br.com.vendas.api.enuns.TipoMovimentacao;
import br.com.vendas.api.repositories.MovimentacaoRepository;
import br.com.vendas.api.requests.ClienteContaRequest;
import br.com.vendas.api.responses.ClienteContaMovimentacaoResponse;
import br.com.vendas.api.responses.ContaMovimentaoResponse;
import br.com.vendas.api.responses.MovimentacaoResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

	private final MovimentacaoRepository repository;

	private final ClienteService clienteService;

	public Movimentacao createMovement(BigDecimal valor, Conta conta, TipoMovimentacao tipo) {
		return Movimentacao.builder().conta(conta).tipoMovimentacao(tipo).valor(valor).build();
	}

	public Movimentacao save(Movimentacao movimentacao) {
		return this.repository.save(movimentacao);
	}

	public ClienteContaMovimentacaoResponse getMovementList(ClienteContaRequest clienteContaRequest) {
		Cliente cliente = this.clienteService.getCustomerByCpf(clienteContaRequest.getCpf());
		Conta conta = this.clienteService.searchAccountByCustomerCpfAndAccountNumber(cliente.getCpf(),
				clienteContaRequest.getNumero());
		List<Movimentacao> movimentacoes = this.clienteService
				.getTransactionListByIdClientAndIdAccount(cliente.getIdCliente(), conta.getIdConta());
		List<MovimentacaoResponse> movimentacaoResponses = this.createMovementResponse(movimentacoes);
		ContaMovimentaoResponse contaMovimentaoResponse =  this.createAccountMovementResponse(conta, movimentacaoResponses);
		return this.createCustomerAccountMovementResponse(cliente, contaMovimentaoResponse);
	}

	private ContaMovimentaoResponse createAccountMovementResponse(Conta conta,
			List<MovimentacaoResponse> movimentacaoResponses) {
		return ContaMovimentaoResponse.builder().idConta(conta.getIdConta()).limite(conta.getLimite())
				.saldo(conta.getSaldo()).tipoConta(conta.getTipoConta()).movimentacoes(movimentacaoResponses)
				.numero(conta.getNumeroConta()).build();
	}

	private List<MovimentacaoResponse> createMovementResponse(List<Movimentacao> movimenttacoes) {
		return movimenttacoes.stream()
				.map(movimenttacao -> MovimentacaoResponse.builder().idMovimentacao(movimenttacao.getIdMovimentacao())
						.dataMovimentacao(movimenttacao.getDataMovimentacao())
						.tipoMovimentacao(movimenttacao.getTipoMovimentacao()).valor(movimenttacao.getValor()).build())
				.collect(Collectors.toList());
	}

	
	private ClienteContaMovimentacaoResponse createCustomerAccountMovementResponse(Cliente cliente,
			ContaMovimentaoResponse contaMovimentaoResponse) {
		return ClienteContaMovimentacaoResponse.builder().cpf(cliente.getCpf()).idCliente(cliente.getIdCliente())
				.dataCadastro(cliente.getDataCadastro()).email(cliente.getEmail()).nome(cliente.getNome())
				.telefone(cliente.getTelefone()).conta(contaMovimentaoResponse).build();
	}
	 

}

