package br.com.zitrus.desafio.service.pojo.form.stockmovement;

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
public class StockMovementForm implements Serializable {
    private static final long serialVersionUID = 5931879257194649322L;

    private Long idProduct;
    private String movementType;
    private BigDecimal saleValue;
    private Integer amountMoved;
    private Integer quantityEntry;
}
