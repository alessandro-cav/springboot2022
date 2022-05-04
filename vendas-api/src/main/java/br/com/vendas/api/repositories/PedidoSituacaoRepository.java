package br.com.vendas.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vendas.api.entities.PedidoSituacao;

public interface PedidoSituacaoRepository extends JpaRepository<PedidoSituacao, Long> {

	@Query("SELECT pedidoSituacao FROM PedidoSituacao pedidoSituacao inner join Cliente cliente on pedidoSituacao.cliente.idCliente = cliente.idCliente inner join Pedido pedido on pedidoSituacao.pedido.idPedido = pedido.idPedido where cliente.idCliente = :idCliente and pedido.idPedido = :idPedido")
	List<PedidoSituacao> demandStatusHistory(@Param("idCliente") Long idCliente, @Param("idPedido") Long idPedido);

}
