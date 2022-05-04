package br.com.vendas.api.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_pedido")
	private Long idPedido;

	@Column(name = "nm_pedido")
	private String numeroPedido;
	
	@Column(name = "data_pedido")
	@CreationTimestamp
	private LocalDateTime dataPedido;

	@ManyToOne
	@JoinColumn(name = "cd_cliente")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "cliente")
	private List<Pagamento> pagamentos;
}
