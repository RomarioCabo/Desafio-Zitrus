package br.com.zitrus.desafio.service.impl.stockmovement;

import br.com.zitrus.desafio.domain.stockmovement.StockMovement;
import br.com.zitrus.desafio.exception.BadRequestException;
import br.com.zitrus.desafio.exception.InternalServerErrorException;
import br.com.zitrus.desafio.repository.stockmovement.StockMovementRepository;
import br.com.zitrus.desafio.service.impl.product.ProductService;
import br.com.zitrus.desafio.service.mapper.StockMovementMapper;
import br.com.zitrus.desafio.service.pojo.dto.stockmovement.ProductReduceDto;
import br.com.zitrus.desafio.service.pojo.dto.stockmovement.ProfitPerProductDto;
import br.com.zitrus.desafio.service.pojo.dto.stockmovement.StockMovementDto;
import br.com.zitrus.desafio.service.pojo.form.stockmovement.StockMovementForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StockMovementService {

  private final StockMovementRepository stockMovementRepository;
  private final StockMovementMapper stockMovementMapper;
  private final ProductService productService;

  private static final String PRODUCT_ENTRY = "Entrada";
  private static final String PRODUCT_OUTPUT = "Saída";

  public StockMovementDto save(StockMovementForm form) {
    productService.productNotFound(form.getIdProduct());

    if (form.getMovementType().equals(PRODUCT_ENTRY)) {
      return incrementAmountStock(form);
    }

    if (form.getMovementType().equals(PRODUCT_OUTPUT)) {
      return decrementAmountStock(form);
    }

    throw new BadRequestException("Tipo de Movimentação inválida! Tipo de Movimentação válidas: Entrada ou Saída");
  }

  public List<ProfitPerProductDto> profitPerProduct() {
    List<StockMovement> stockMovements = stockMovementRepository.findByMovementType(PRODUCT_OUTPUT);

    if (stockMovements.isEmpty()) {
      return null;
    }

    List<ProfitPerProductDto> profitProducts = new ArrayList<>();

    stockMovements.forEach(
        obj ->
            profitProducts.add(
                ProfitPerProductDto.builder()
                    .product(
                        ProductReduceDto.builder()
                            .id(obj.getProduct().getIdProduct())
                            .name(obj.getProduct().getName())
                            .build())
                    .totalOutputQuantity(
                        getTotalOutputQuantity(stockMovements, obj.getProduct().getIdProduct()))
                    .profitPerProduct(
                        getProfitPerProduct(stockMovements, obj.getProduct().getIdProduct()))
                    .build()));

    return profitProducts.stream().distinct().collect(Collectors.toList());
  }

  private Integer getTotalOutputQuantity(List<StockMovement> stockMovements, Long idProduct) {
    return stockMovements.stream()
        .filter(obj -> obj.getProduct().getIdProduct() == idProduct)
        .mapToInt(StockMovement::getAmountMoved)
        .sum();
  }

  private BigDecimal getProfitPerProduct(List<StockMovement> stockMovements, Long idProduct) {
    return stockMovements.stream()
        .filter(obj -> obj.getProduct().getIdProduct() == idProduct)
        .map(item -> item.getSaleValue().subtract(item.getProduct().getSupplierValue()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private StockMovementDto incrementAmountStock(StockMovementForm form) {
    StockMovement stockMovement = mapper(form);

    stockMovement = save(stockMovement);

    productService.incrementAmountStock(form.getIdProduct(), form.getQuantityEntry());

    return mapper(stockMovement.getIdStockMovement());
  }

  private StockMovementDto decrementAmountStock(StockMovementForm form) {
    StockMovement stockMovement = mapper(form);
    stockMovement.setSaleDate(LocalDateTime.now());

    stockMovement = save(stockMovement);

    productService.decrementAmountStock(form.getIdProduct(), form.getAmountMoved());

    return mapper(stockMovement.getIdStockMovement());
  }

  private StockMovement mapper(StockMovementForm form) {
    try {
      return stockMovementMapper.toEntity(form);
    } catch (Exception e) {
      throw new InternalServerErrorException("Não foi realizar o Mapper para entity!");
    }
  }

  private StockMovement save(StockMovement stockMovement) {
    try {
      return stockMovementRepository.save(stockMovement);
    } catch (Exception e) {
      throw new InternalServerErrorException("Não foi possível salvar!");
    }
  }

  private StockMovementDto mapper(Long idStockMovement) {
    StockMovement stockMovement = stockMovementRepository.findByIdStockMovement(idStockMovement);
    stockMovement.setProduct(
        productService.findProductById(stockMovement.getProduct().getIdProduct()));

    try {
      return stockMovementMapper.toDto(stockMovement);
    } catch (Exception e) {
      throw new InternalServerErrorException("Não foi realizar o Mapper para DTO!");
    }
  }
}
