package br.com.zitrus.desafio.repository.product.criteria.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilter implements Serializable {
    private static final long serialVersionUID = 2653309884011799079L;

    private Long idProduct;
    private String nameProduct;
    private BigDecimal supplierValue;
    private Integer quantityStock;

    private Long idCategory;
    private String nameCategory;
}
