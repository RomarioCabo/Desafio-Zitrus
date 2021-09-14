package br.com.zitrus.desafio.repository.product.criteria;

import br.com.zitrus.desafio.repository.product.criteria.pojo.ProductFilter;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.pojo.dto.product.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

  Page<ProductDto> filterProduct(ProductFilter filter, Pageable pageable, Direction direction);
}
