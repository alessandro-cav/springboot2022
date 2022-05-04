package br.com.vendas.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vendas.api.entities.Produto;
import br.com.vendas.api.handlers.BadRequestException;
import br.com.vendas.api.handlers.ProdutoNotFoundException;
import br.com.vendas.api.repositories.ProdutoRepository;
import br.com.vendas.api.requests.ProdutoRequest;
import br.com.vendas.api.responses.ProdutoResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

	private final ProdutoRepository repository;

	public ProdutoResponse save(ProdutoRequest produtoRequest) {
		this.checkIfCodeAlreadyExists(produtoRequest.getCodigo());
		Produto produto = this.createProduct(produtoRequest);
		produto = this.repository.save(produto);
		return this.createProductResponse(produto);
	}

	public ProdutoResponse findByCodigo(String codigo) {
		Produto produto = this.findProductByCode(codigo);
		return this.createProductResponse(produto);
	}

	public List<ProdutoResponse> findAll(Pageable pageable) {
		List<Produto> produtos = this.repository.findAll(pageable).toList();
	return produtos.stream().map(produto -> this.createProductResponse(produto))
				.collect(Collectors.toList());
	}

	public void delete(String codigo) {
		Produto produto = this.findProductByCode(codigo);
		this.repository.delete(produto);
	}
	
	
	public ProdutoResponse update(ProdutoRequest produtoRequest) {
		Produto produto = this.findProductByCode(produtoRequest.getCodigo());
		produto.setNome(produtoRequest.getNome());
		produto.setDescricao(produtoRequest.getDescricao());
		produto.setPreco(produtoRequest.getPreco());
		
		produto = this.repository.save(produto);
		return this.createProductResponse(produto);
	}
	

	public Produto findProductByCode(String codigo) {
		return this.repository.findByCodigoProduto(codigo).orElseThrow(() -> {
			throw new ProdutoNotFoundException("Produto não encontrado com esse codigo " + codigo);
		});
	}

	public ProdutoResponse createProductResponse(Produto produto) {
		return ProdutoResponse.builder().idProduto(produto.getIdProduto()).nome(produto.getNome())
				.descricao(produto.getDescricao()).preco(produto.getPreco()).codigo(produto.getCodigoProduto()).build();
	}

	private Produto createProduct(ProdutoRequest produtoRequest) {
		return Produto.builder().nome(produtoRequest.getNome()).codigoProduto(produtoRequest.getCodigo()).descricao(produtoRequest.getDescricao())
				.preco(produtoRequest.getPreco()).build();
	}
	
	private void checkIfCodeAlreadyExists(String codigo) {
		this.repository.findByCodigoProduto(codigo).ifPresent(produto -> {
			throw new BadRequestException("Produto já cadastrado  com esse codigo: " + codigo);
		});
		
	}

	public List<Produto> findAllProductsByIdItemDemand(String numeroItemPedido) {
		List<Produto> produtos = this.repository.findAllProductsByIdItemDemand(numeroItemPedido);
		if(produtos.isEmpty())
			throw new ProdutoNotFoundException("Lista de produtos vazia.");
		
		return produtos;
	}
}
