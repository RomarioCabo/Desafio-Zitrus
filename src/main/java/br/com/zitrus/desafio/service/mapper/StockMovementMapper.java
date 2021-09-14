package br.com.zitrus.desafio.service.mapper;

import br.com.zitrus.desafio.domain.stockmovement.StockMovement;
import br.com.zitrus.desafio.service.pojo.dto.stockmovement.StockMovementDto;
import br.com.zitrus.desafio.service.pojo.form.stockmovement.StockMovementForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface StockMovementMapper {

    @Mapping(target = "product.idProduct", source = "stockMovementForm.idProduct")
    StockMovement toEntity(StockMovementForm stockMovementForm);

    StockMovementDto toDto(StockMovement stockMovement);
}
