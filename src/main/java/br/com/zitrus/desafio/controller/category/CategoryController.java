package br.com.zitrus.desafio.controller.category;

import br.com.zitrus.desafio.controller.helper.ApiHelper;
import br.com.zitrus.desafio.exception.StandardError;
import br.com.zitrus.desafio.repository.category.criteria.pojo.CategoryFilter;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.pojo.dto.category.CategoryDto;
import br.com.zitrus.desafio.service.pojo.form.category.CategoryForm;
import br.com.zitrus.desafio.service.impl.category.CategoryService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController extends ApiHelper<CategoryDto> {

  private final CategoryService categoryService;

  @Value("${application.url}")
  private String url;

  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created", response = CategoryDto.class),
        @ApiResponse(code = 400, message = "Bad Request", response = StandardError.class),
        @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
      })
  @PostMapping(
      value = "/save",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryForm request) {
    CategoryDto categoryDto = categoryService.save(request, null);

    return ResponseEntity.created(getUri(url + "api/v1/category", "id={id}", categoryDto.getId()))
        .body(categoryDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Updated", response = CategoryDto.class),
        @ApiResponse(code = 400, message = "Bad Request", response = StandardError.class),
        @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
      })
  @PutMapping(
      value = "/update/{idCategory}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryDto> update(
      @PathVariable Long idCategory, @Valid @RequestBody CategoryForm request) {

    return ResponseEntity.ok(categoryService.save(request, idCategory));
  }

  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Deleted"),
        @ApiResponse(code = 400, message = "Bad Request", response = StandardError.class),
        @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
      })
  @DeleteMapping(value = "/delete/{idCategory}")
  public ResponseEntity<Void> delete(@PathVariable Long idCategory) {
    categoryService.delete(idCategory);

    return ResponseEntity.ok().build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "OK", response = CategoryDto[].class),
        @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CategoryDto>> findCategories(
      @ModelAttribute CategoryFilter filters,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "name") String sortBy,
      @RequestParam(value = "direction", defaultValue = "ASC") Direction direction) {

    Page<CategoryDto> categoriesPage =
        categoryService.find(filters, page, linesPerPage, sortBy, direction);

    return ResponseEntity.ok()
        .headers(responseHeaders(categoriesPage))
        .body(categoriesPage.getContent());
  }
}
