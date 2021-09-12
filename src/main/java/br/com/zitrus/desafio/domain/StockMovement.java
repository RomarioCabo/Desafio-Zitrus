package br.com.zitrus.desafio.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_movement", catalog = "stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement  {

  private static final long serialVersionUID = 549138284534763022L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "id_product", nullable = false)
  private Product product;

  @Column(name = "movement_type", nullable = false)
  private String movementType;

  @Column(name = "sale_value", nullable = false, precision = 12, scale = 2)
  private BigDecimal saleValue;

  @Column(name = "sale_date", nullable = false)
  private LocalDateTime saleDate;

  @Column(name = "amount_moved", nullable = false)
  private long amountMoved;
}
