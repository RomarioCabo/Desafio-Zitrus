package br.com.zitrus.desafio.service.pojo.form.product;

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
public class ProductForm implements Serializable {
  private static final long serialVersionUID = -9100818929415120481L;

  private String name;
  private BigDecimal supplierValue;
  private short quantityStock;
  private long idCategory;
}
