package br.com.zitrus.desafio.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "product",
    catalog = "stock",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractEntity<Long> {

  private static final long serialVersionUID = -8173181948328519803L;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "supplier_value", nullable = false, precision = 12, scale = 2)
  private BigDecimal supplierValue;

  @Column(name = "quantity_stock")
  private short quantityStock;

  @ManyToOne
  @JoinColumn(name = "id_category", nullable = false)
  private Category category;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
