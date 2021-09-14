package br.com.zitrus.desafio.repository.product.criteria.impl;

import br.com.zitrus.desafio.domain.category.Category_;
import br.com.zitrus.desafio.domain.product.Product;
import br.com.zitrus.desafio.domain.product.Product_;
import br.com.zitrus.desafio.repository.product.criteria.ProductRepositoryCustom;
import br.com.zitrus.desafio.repository.product.criteria.pojo.ProductFilter;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.mapper.ProductMapper;
import br.com.zitrus.desafio.service.pojo.dto.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

  private final EntityManager entityManager;
  private final ProductMapper productMapper;

  @Override
  public Page<ProductDto> filterProduct(
      ProductFilter filter, Pageable pageable, Direction direction) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

    Root<Product> root = criteriaQuery.from(Product.class);

    criteriaQuery.multiselect(
        root.get(Product_.ID_PRODUCT),
        root.get(Product_.NAME),
        root.get(Product_.SUPPLIER_VALUE),
        root.get(Product_.QUANTITY_STOCK),
        root.get(Product_.CATEGORY),
        root.get(Product_.CREATED_AT),
        root.get(Product_.UPDATED_AT));

    List<Predicate> predicates = new ArrayList<>();

    if (filter.getIdProduct() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Product_.ID_PRODUCT), filter.getIdProduct()));
    }

    if (filter.getNameProduct() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Product_.NAME), filter.getNameProduct()));
    }

    if (filter.getSupplierValue() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Product_.SUPPLIER_VALUE), filter.getSupplierValue()));
    }

    if (filter.getQuantityStock() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Product_.QUANTITY_STOCK), filter.getQuantityStock()));
    }

    if (filter.getIdCategory() != null) {
      predicates.add(
          criteriaBuilder.equal(root.get(Product_.CATEGORY).get(Category_.ID_CATEGORY), filter.getIdCategory()));
    }

    if (filter.getNameCategory() != null) {
      predicates.add(
          criteriaBuilder.equal(root.get(Product_.CATEGORY).get(Category_.NAME), filter.getNameCategory()));
    }

    if (!predicates.isEmpty()) criteriaQuery.where(predicates.toArray(new Predicate[0]));

    criteriaQuery.orderBy(getOrdering(pageable, criteriaBuilder, root, direction));

    TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);

    int totalRows = typedQuery.getResultList().size();
    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    typedQuery.setMaxResults(pageable.getPageSize());

    List<ProductDto> products = productMapper.tupleToDto(typedQuery.getResultList());

    if (products.isEmpty()) return null;

    return new PageImpl<>(products, pageable, totalRows);
  }

  private Order getOrdering(
      Pageable pageable, CriteriaBuilder criteriaBuilder, Root<Product> root, Direction direction) {
    if (direction == Direction.ASC) {
      return criteriaBuilder.asc(root.get(getTableName(pageable.getSort().toString())));
    }

    return criteriaBuilder.desc(root.get(getTableName(pageable.getSort().toString())));
  }

  private String getTableName(String sort) {
    switch (sort) {
      case "idProduct":
        return "idProduct";
      case "name":
        return "name";
      case "supplierValue":
        return "supplierValue";
      case "quantityStock":
        return "quantityStock";
      default:
        return "idCategory";
    }
  }
}
