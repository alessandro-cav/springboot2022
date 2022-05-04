package br.com.vendas.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.PedidoSituacao;
import br.com.vendas.api.handlers.PedidoNotFoundException;
import br.com.vendas.api.repositories.PedidoSituacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoSituacaoService {

	private final PedidoSituacaoRepository repository;

	public PedidoSituacao save(PedidoSituacao pedidoSituacao) {
		return this.repository.save(pedidoSituacao);
	}

	public List<PedidoSituacao> demandStatusHistory(Long idCliente, Long idPedido) {
		List<PedidoSituacao> situacaos = this.repository.demandStatusHistory(idCliente, idPedido);
		if (situacaos.isEmpty()) {
			throw new PedidoNotFoundException("Situação de pedido sem informação");
		}
		return situacaos;
	}

}
