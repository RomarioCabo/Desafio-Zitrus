package br.com.zitrus.desafio.repository.category;

import br.com.zitrus.desafio.domain.category.Category;
import br.com.zitrus.desafio.repository.category.criteria.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository
    extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

  @Transactional(readOnly = true)
  boolean existsByName(String name);
}
