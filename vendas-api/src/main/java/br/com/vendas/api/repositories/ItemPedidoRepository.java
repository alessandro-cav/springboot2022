package br.com.vendas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vendas.api.entities.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

	@Query("SELECT itemPedido FROM ItemPedido itemPedido inner join Pedido pedido on itemPedido.pedido.idPedido = pedido.idPedido inner join Produto produto on itemPedido.produto.idProduto = produto.idProduto where pedido.idPedido = :idPedido and produto.idProduto = :idProduto")
	ItemPedido getItemRequestByOrderNumberAndProductCode(@Param("idPedido") Long idPedido,
			@Param("idProduto") Long idProduto);
}
