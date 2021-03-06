package br.com.zitrus.desafio.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import br.com.zitrus.desafio.domain.category.Category;
import br.com.zitrus.desafio.domain.stockmovement.StockMovement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
public class Product {

  private static final long serialVersionUID = -8173181948328519803L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_product")
  private long idProduct;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "supplier_value", nullable = false, precision = 12, scale = 2)
  private BigDecimal supplierValue;

  @Column(name = "quantity_stock")
  private Integer quantityStock;

  @ManyToOne
  @JoinColumn(name = "id_category", nullable = false)
  private Category category;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StockMovement> stockMovement = new ArrayList<>();
}
