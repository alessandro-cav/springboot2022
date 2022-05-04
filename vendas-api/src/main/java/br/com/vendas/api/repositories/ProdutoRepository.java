package br.com.vendas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vendas.api.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findByCodigoProduto(String codigo);

	@Query("SELECT produto FROM Produto produto inner join ItemPedido itemPedido on itemPedido.produto.idProduto = produto.idProduto  where itemPedido.numeroItemPedido = :numeroItemPedido")
	List<Produto> findAllProductsByIdItemDemand(@Param("numeroItemPedido") String numeroItemPedido);

	
}
