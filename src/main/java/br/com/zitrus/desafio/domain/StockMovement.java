package br.com.zitrus.desafio.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_movement", catalog = "stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StockMovement extends AbstractEntity<Long> {

  private static final long serialVersionUID = 549138284534763022L;

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
