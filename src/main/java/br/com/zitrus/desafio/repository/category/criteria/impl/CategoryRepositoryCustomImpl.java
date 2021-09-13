package br.com.zitrus.desafio.repository.category.criteria.impl;

import br.com.zitrus.desafio.domain.category.Category;
import br.com.zitrus.desafio.domain.Category_;
import br.com.zitrus.desafio.repository.category.criteria.CategoryRepositoryCustom;
import br.com.zitrus.desafio.repository.category.criteria.pojo.CategoryFilter;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.mapper.CategoryMapper;
import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
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
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final EntityManager entityManager;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> filterCategory(CategoryFilter filter, Pageable pageable, Direction direction) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<Category> root = criteriaQuery.from(Category.class);

        criteriaQuery.multiselect(
                root.get(Category_.ID_CATEGORY),
                root.get(Category_.NAME),
                root.get(Category_.CREATED_AT),
                root.get(Category_.UPDATED_AT)
        );

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get(Category_.ID_CATEGORY), filter.getId()));
        }

        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get(Category_.NAME), filter.getName()));
        }

        if (!predicates.isEmpty()) criteriaQuery.where(predicates.toArray(new Predicate[0]));

        criteriaQuery.orderBy(getOrdering(pageable, criteriaBuilder, root, direction));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);

        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<CategoryDto> categories = categoryMapper.tupleToDto(typedQuery.getResultList());

        if (categories.isEmpty()) return null;

        return new PageImpl<>(categories, pageable, totalRows);
    }

    private Order getOrdering(Pageable pageable, CriteriaBuilder criteriaBuilder, Root<Category> root, Direction direction) {
        if (direction == Direction.ASC) {
            return criteriaBuilder.asc(root.get(getTableName(pageable.getSort().toString())));
        }

        return criteriaBuilder.desc(root.get(getTableName(pageable.getSort().toString())));
    }

    private String getTableName(String sort) {
        if (Category_.NAME.equals(sort)) {
            return Category_.NAME;
        }

        return Category_.ID_CATEGORY;
    }
}
