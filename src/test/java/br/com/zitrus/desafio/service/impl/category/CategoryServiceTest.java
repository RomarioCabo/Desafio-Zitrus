package br.com.zitrus.desafio.service.impl.category;

import br.com.zitrus.desafio.domain.Category;
import br.com.zitrus.desafio.exception.BadRequestException;
import br.com.zitrus.desafio.exception.InternalServerErrorException;
import br.com.zitrus.desafio.repository.category.CategoryRepository;
import br.com.zitrus.desafio.service.mapper.CategoryMapper;
import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
import br.com.zitrus.desafio.service.pojo.form.category.CategoryForm;
import br.com.zitrus.desafio.util.CategoryMocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryMapper categoryMapper;

    @SpyBean
    private CategoryService categoryService;

    @Test
    void shouldSaveCategory() {
        Mockito.when(categoryRepository.existsByName(Mockito.anyString())).thenReturn(false);

        Mockito.when(categoryMapper.toEntity(Mockito.any(CategoryForm.class)))
                .thenReturn(CategoryMocked.builderCategory());
        Category category = categoryMapper.toEntity(CategoryMocked.builderCategoryForm());

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        Mockito.when(categoryService.save(CategoryMocked.builderCategoryForm(), null))
                .thenReturn(CategoryMocked.builderCategoryDto("Categoria Mocked", null));
        CategoryDto productDto = categoryService.save(CategoryMocked.builderCategoryForm(), null);

        Assertions.assertThat(productDto).isNotNull();
        Assertions.assertThat(productDto.getId()).isEqualTo(1L);
        Assertions.assertThat(productDto.getName()).isEqualTo("Categoria Mocked");
        Assertions.assertThat(productDto.getCreatedAt()).isEqualTo(LocalDateTime.parse("2021-09-11T18:29:56.899449"));
        Assertions.assertThat(productDto.getUpdatedAt()).isNull();
    }

    @Test
    void shouldUpdateCategory() {
        Mockito.when(categoryRepository.existsByName(Mockito.anyString())).thenReturn(false);

        Mockito.when(categoryRepository.findById(Mockito.anyLong()))
                .thenReturn(CategoryMocked.builderCategoryOptional());

        Mockito.when(categoryMapper.toEntity(Mockito.any(CategoryForm.class)))
                .thenReturn(CategoryMocked.builderCategory());
        Category category = categoryMapper.toEntity(CategoryMocked.builderCategoryForm());

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        Mockito.when(categoryService.save(CategoryMocked.builderCategoryForm(), 1L))
                .thenReturn(CategoryMocked.builderCategoryDto("Categoria Mocked Updated",
                        LocalDateTime.parse("2021-10-11T18:29:56.899449")));
        CategoryDto productDto = categoryService.save(CategoryMocked.builderCategoryForm(), 1L);

        Assertions.assertThat(productDto).isNotNull();
        Assertions.assertThat(productDto.getId()).isEqualTo(1L);
        Assertions.assertThat(productDto.getName()).isEqualTo("Categoria Mocked Updated");
        Assertions.assertThat(productDto.getCreatedAt()).isEqualTo(LocalDateTime.parse("2021-09-11T18:29:56.899449"));
        Assertions.assertThat(productDto.getUpdatedAt()).isEqualTo(LocalDateTime.parse("2021-10-11T18:29:56.899449"));
    }

    @Test
    void shouldThrowExceptionIfCategoryAlreadyExists() {
        Mockito.doThrow(new BadRequestException("Categoria já cadastrada em nossa base de dados!"))
                .when(categoryService).save(CategoryMocked.builderCategoryForm(), null);

        Throwable exception = Assertions.catchThrowable(() -> categoryService
                .save(CategoryMocked.builderCategoryForm(), null));

        Assertions.assertThat(exception)
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Categoria já cadastrada em nossa base de dados!");
    }

    @Test
    void shouldThrowExceptionIfCategoryNotFound() {
        Mockito.doThrow(new BadRequestException("Categoria não localizado em nossa base de dados!"))
                .when(categoryService).save(CategoryMocked.builderCategoryForm(), null);

        Throwable exception = Assertions.catchThrowable(() -> categoryService
                .save(CategoryMocked.builderCategoryForm(), null));

        Assertions.assertThat(exception)
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Categoria não localizado em nossa base de dados!");
    }

    @Test
    void shouldThrowExceptionWhenDontSave() {
        Mockito.doThrow(new InternalServerErrorException("Não foi possível salvar!"))
                .when(categoryService).save(CategoryMocked.builderCategoryForm(), null);

        Throwable exception = Assertions.catchThrowable(() -> categoryService
                .save(CategoryMocked.builderCategoryForm(), null));

        Assertions.assertThat(exception)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Não foi possível salvar!");
    }

    @Test
    void shouldThrowExceptionWhenDontMapperToEntity() {
        Mockito.doThrow(new InternalServerErrorException("Não foi possível realizar o Mapper para entidade!"))
                .when(categoryService).save(CategoryMocked.builderCategoryForm(), null);

        Throwable exception = Assertions.catchThrowable(() -> categoryService
                .save(CategoryMocked.builderCategoryForm(), null));

        Assertions.assertThat(exception)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Não foi possível realizar o Mapper para entidade!");
    }

    @Test
    void shouldThrowExceptionWhenDontMapperToDto() {
        Mockito.doThrow(new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!"))
                .when(categoryService).save(CategoryMocked.builderCategoryForm(), null);

        Throwable exception = Assertions.catchThrowable(() -> categoryService
                .save(CategoryMocked.builderCategoryForm(), null));

        Assertions.assertThat(exception)
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Não foi possível realizar o Mapper para DTO!");
    }
}
