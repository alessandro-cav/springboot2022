package br.com.vendas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vendas.api.entities.Cliente;
import br.com.vendas.api.entities.Conta;
import br.com.vendas.api.entities.Endereco;
import br.com.vendas.api.entities.Movimentacao;
import br.com.vendas.api.entities.Pedido;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByCpf(String cpf);

	Optional<Cliente> findByEmail(String email);

	Optional<Cliente> findByTelefone(String telefone);

	@Query("SELECT endereco FROM Endereco endereco inner join Cliente cliente on endereco.cliente.idCliente = cliente.idCliente  where cliente.idCliente = :idCliente")
	List<Endereco> getAddressListByCustomerId(@Param("idCliente") Long idCliente);

	@Query("SELECT endereco FROM Endereco endereco inner join Cliente cliente on endereco.cliente.cpf = cliente.cpf  where cliente.cpf = :cpf and endereco.codigoEndereco = :codigo")
	Endereco searchAddressByCpfAnCodeAddress(@Param("cpf") String cpfCnpj, @Param("codigo") String codigo);

	@Query("SELECT pedido FROM Pedido pedido inner join Cliente cliente on pedido.cliente.idCliente = cliente.idCliente  where cliente.idCliente = :idCliente")
	List<Pedido> getRequestListByCustomerId(Long idCliente);

	@Query("SELECT pedido FROM Pedido pedido inner join Cliente cliente on pedido.cliente.cpf = cliente.cpf  where cliente.cpf = :cpf and pedido.numeroPedido = :numero")
	Pedido searchDemandByCpfAnNumberDemand(@Param("cpf") String cpfCnpj, @Param("numero") String numero);

	@Query("SELECT conta FROM Conta conta inner join Cliente cliente on conta.cliente.idCliente = cliente.idCliente  where cliente.idCliente = :idCliente")
	List<Conta> fetchAccountListByCustomerId(Long idCliente);

	@Query("SELECT conta FROM Conta conta inner join Cliente cliente on conta.cliente.cpf = cliente.cpf  where cliente.cpf = :cpf and conta.numeroConta = :numero")
	Conta searchAccountByCustomerIdAndAccountId(String cpf, String numero);

	@Query("SELECT movimentacao FROM Movimentacao movimentacao inner join Conta conta on movimentacao.conta.idConta = conta.idConta inner join Cliente cliente on conta.cliente.idCliente = cliente.idCliente where cliente.idCliente = :idCliente and conta.idConta = :idConta")
	List<Movimentacao> getTransactionListByIdClientAndIdAccount(@Param("idCliente") Long idCliente,@Param("idConta") Long idConta);

	@Query("SELECT pedido FROM Pedido pedido inner join Cliente cliente on pedido.cliente.cpf = cliente.cpf  where cliente.cpf = :cpf  and pedido.numeroPedido = :numeroPedido")
	Pedido searchRequestByCpfAndNumberDemand(String cpf, String numeroPedido);




}
