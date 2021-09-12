package br.com.zitrus.desafio.util;

import br.com.zitrus.desafio.domain.Category;
import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
import br.com.zitrus.desafio.service.pojo.form.category.CategoryForm;

import java.time.LocalDateTime;
import java.util.Optional;

public class CategoryMocked {

    public static CategoryForm builderCategoryForm() {
        return CategoryForm.builder()
                .name("Categoria Mocked")
                .build();
    }

    public static Category builderCategory() {
        return Category.builder()
                .id(1L)
                .name("Categoria Mocked")
                .createdAt(LocalDateTime.parse("2021-09-11T18:29:56.899449"))
                .build();
    }

    public static Optional<Category> builderCategoryOptional() {
        return Optional.of(builderCategory());
    }

    public static CategoryDto builderCategoryDto(String nameCategory, LocalDateTime updatedAt) {
        return CategoryDto.builder()
                .id(1L)
                .name(nameCategory)
                .createdAt(LocalDateTime.parse("2021-09-11T18:29:56.899449"))
                .updatedAt(updatedAt)
                .build();
    }
}
