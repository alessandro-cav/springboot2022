package br.com.vendas.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_item_pedido")
	private Long idItemPedido;
	
	private String numeroItemPedido;
	
	@ManyToOne
	@JoinColumn(name = "cd_produto")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "cd_pedido")
	private Pedido pedido;
	
	private Integer quantidade;
	
	private BigDecimal total;

}
