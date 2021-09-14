package br.com.zitrus.desafio.repository.product;

import br.com.zitrus.desafio.domain.product.Product;
import br.com.zitrus.desafio.repository.product.criteria.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

  @Transactional(readOnly = true)
  boolean existsByName(String name);
}
