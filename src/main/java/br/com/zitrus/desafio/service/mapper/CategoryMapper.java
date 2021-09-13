package br.com.zitrus.desafio.service.mapper;

import br.com.zitrus.desafio.domain.category.Category;
import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
import br.com.zitrus.desafio.service.pojo.form.category.CategoryForm;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.persistence.Tuple;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category toEntity(CategoryForm categoryForm);

  @Mapping(target = "id", source = "category.idCategory")
  CategoryDto toDto(Category category);

  List<CategoryDto> toDto(List<Category> categories);

  @Mapping(target = "id",        expression = "java(tuple.get(0, Long.class))")
  @Mapping(target = "name",      expression = "java(tuple.get(1, String.class))")
  @Mapping(target = "createdAt", expression = "java(tuple.get(2, java.time.LocalDateTime.class))")
  @Mapping(target = "updatedAt", expression = "java(tuple.get(3, java.time.LocalDateTime.class))")
  CategoryDto tupleToDto(Tuple tuple);

  List<CategoryDto> tupleToDto(List<Tuple> tuples);
}
