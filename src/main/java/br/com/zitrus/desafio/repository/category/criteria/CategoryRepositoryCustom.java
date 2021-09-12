package br.com.zitrus.desafio.repository.category.criteria;

import br.com.zitrus.desafio.repository.category.criteria.pojo.CategoryFilter;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

    Page<CategoryDto> filterCategory(CategoryFilter filter, Pageable pageable, Direction direction);
}
