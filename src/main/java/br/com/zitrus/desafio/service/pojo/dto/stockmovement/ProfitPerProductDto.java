package br.com.zitrus.desafio.service.pojo.dto.stockmovement;

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
public class ProfitPerProductDto implements Serializable {
    private static final long serialVersionUID = -3187271001323785486L;

    private ProductReduceDto product;
    private Integer totalOutputQuantity;
    private BigDecimal profitPerProduct;
}
