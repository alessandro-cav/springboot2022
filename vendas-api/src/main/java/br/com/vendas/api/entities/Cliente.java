package br.com.vendas.api.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_cliente")
	private Long idCliente;

	private String cpf;
	
	private String nome;
	
	private String email;
	
	private String telefone;
	
	@CreationTimestamp
	private LocalDateTime dataCadastro;
	
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;

	@OneToMany(mappedBy = "cliente")
	private List<Conta> contas;

	@OneToMany(mappedBy = "cliente")
	private List<Endereco> enderecos;
	
	@OneToMany(mappedBy = "cliente")
	private List<Pagamento> pagamentos;

}
