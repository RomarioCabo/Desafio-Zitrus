package br.com.zitrus.desafio.service.pojo.dto.stockmovement;

import br.com.zitrus.desafio.service.pojo.dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementDto implements Serializable {
    private static final long serialVersionUID = 1266624211477875867L;

    private Long idStockMovement;
    private ProductDto product;
    private String movementType;
    private BigDecimal saleValue;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime saleDate;
    private Integer amountMoved;
}
