package br.com.zitrus.desafio.service.mapper;

import br.com.zitrus.desafio.domain.product.Product;
import br.com.zitrus.desafio.service.pojo.dto.product.ProductDto;
import br.com.zitrus.desafio.service.pojo.form.product.ProductForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.persistence.Tuple;
import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {

  @Mapping(target = "category.idCategory", source = "productForm.idCategory")
  Product toEntity(ProductForm productForm);

  @Mapping(target = "id", source = "product.idProduct")
  ProductDto toDto(Product product);

  List<ProductDto> toDto(List<Product> products);

  @Mapping(target = "id", expression = "java(tuple.get(0, Long.class))")
  @Mapping(target = "name", expression = "java(tuple.get(1, String.class))")
  @Mapping(target = "supplierValue", expression = "java(tuple.get(2, java.math.BigDecimal.class))")
  @Mapping(target = "quantityStock", expression = "java(tuple.get(3, Integer.class))")
  @Mapping(
      target = "category",
      expression =
          "java(this.categoryMapper.toDto(tuple.get(4, br.com.zitrus.desafio.domain.category.Category.class)))")
  @Mapping(target = "createdAt", expression = "java(tuple.get(5, java.time.LocalDateTime.class))")
  @Mapping(target = "updatedAt", expression = "java(tuple.get(6, java.time.LocalDateTime.class))")
  ProductDto tupleToDto(Tuple tuple);

  List<ProductDto> tupleToDto(List<Tuple> tuples);
}
