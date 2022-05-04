package br.com.vendas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.api.entities.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

}
