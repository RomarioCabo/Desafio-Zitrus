package br.com.zitrus.desafio.service.pojo.dto.product;

import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
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
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 4800078331009671314L;

    private long id;
    private String name;
    private BigDecimal supplierValue;
    private Integer quantityStock;
    private CategoryDto category;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
