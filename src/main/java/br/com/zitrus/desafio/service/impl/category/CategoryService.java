package br.com.zitrus.desafio.service.impl.category;

import static java.util.Objects.isNull;

import br.com.zitrus.desafio.domain.category.Category;
import br.com.zitrus.desafio.exception.BadRequestException;
import br.com.zitrus.desafio.exception.InternalServerErrorException;
import br.com.zitrus.desafio.repository.category.CategoryRepository;
import br.com.zitrus.desafio.repository.category.criteria.pojo.CategoryFilter;
import br.com.zitrus.desafio.service.interfaces.CrudInterface;
import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.pojo.form.category.CategoryForm;
import br.com.zitrus.desafio.service.mapper.CategoryMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements CrudInterface<CategoryDto, CategoryForm, CategoryFilter> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CategoryForm form, Long id) {
        categoryAlreadyExists(form.getName());

        categoryNotFound(id);

        return mapperCategory(save(mapperCategory(form, id)));
    }

    @Override
    public void delete(Long id) {
        categoryNotFound(id);

        deleteById(id);
    }

    private void deleteById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível excluir o registro!");
        }
    }

    @Override
    public Page<CategoryDto> find(
            CategoryFilter categoryFilter,
            Integer page,
            Integer linesPerPage,
            String sortBy,
            Direction direction) {
        try {
            Pageable pageable = PageRequest.of(page, linesPerPage, Sort.by(sortBy));

            Page<CategoryDto> categoriesPage = categoryRepository.filterCategory(categoryFilter, pageable, direction);

            if (categoriesPage.isEmpty()) {
                return null;
            }

            return categoriesPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Não foi possível retornar a lista de categorias!");
        }
    }

    private void categoryAlreadyExists(String nameCategory) {
        if (categoryRepository.existsByName(nameCategory)) {
            throw new BadRequestException("Categoria já cadastrada em nossa base de dados!");
        }
    }

    private void categoryNotFound(Long id) {
        if (!isNull(id)) {
            Optional<Category> category = categoryRepository.findById(id);

            if (!category.isPresent()) {
                throw new BadRequestException("Categoria não localizado em nossa base de dados!");
            }
        }
    }

    private Category save(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível salvar!");
        }
    }

    private Category mapperCategory(CategoryForm form, Long id) {
        try {
            Category category;

            category = categoryMapper.toEntity(form);

            if (isNull(id)) {
                category.setCreatedAt(LocalDateTime.now());
            }

            if (!isNull(id)) {
                Optional<Category> categoryOptional = categoryRepository.findById(id);

                category.setIdCategory(id);
                category.setCreatedAt(categoryOptional.get().getCreatedAt());
                category.setUpdatedAt(LocalDateTime.now());
            }

            return category;
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para entidade!");
        }
    }

    private CategoryDto mapperCategory(Category category) {
        try {
            return categoryMapper.toDto(category);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!");
        }
    }
}
