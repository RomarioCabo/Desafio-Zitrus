package br.com.zitrus.desafio.controller.product;

import br.com.zitrus.desafio.controller.helper.ApiHelper;
import br.com.zitrus.desafio.exception.StandardError;
import br.com.zitrus.desafio.repository.product.criteria.pojo.ProductFilter;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.impl.product.ProductService;
import br.com.zitrus.desafio.service.pojo.dto.product.ProductDto;
import br.com.zitrus.desafio.service.pojo.form.product.ProductForm;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController extends ApiHelper<ProductDto> {

    private final ProductService productService;

    @Value("${application.url}")
    private String url;

    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Created", response = ProductDto.class),
                    @ApiResponse(code = 400, message = "Bad Request", response = StandardError.class),
                    @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
            })
    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductForm request) {
        ProductDto productDtoDto = productService.save(request, null);

        return ResponseEntity.created(getUri(url + "api/v1/product", "id={id}", productDtoDto.getId())).body(productDtoDto);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Updated", response = ProductDto.class),
                    @ApiResponse(code = 400, message = "Bad Request", response = StandardError.class),
                    @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
            })
    @PutMapping(
            value = "/update/{idProduct}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> update(@PathVariable Long idProduct,
                                             @Valid @RequestBody ProductForm request) {

        return ResponseEntity.ok(productService.save(request, idProduct));
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Deleted"),
                    @ApiResponse(code = 400, message = "Bad Request", response = StandardError.class),
                    @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
            })
    @DeleteMapping(value = "/delete/{idProduct}")
    public ResponseEntity<Void> delete(@PathVariable Long idProduct) {
        productService.delete(idProduct);

        return ResponseEntity.ok().build();
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = ProductDto[].class),
                    @ApiResponse(code = 500, message = "Internal Error", response = StandardError.class)
            })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> findCategories(@ModelAttribute ProductFilter filters,
                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
                                                            @RequestParam(value = "orderBy", defaultValue = "name") String sortBy,
                                                            @RequestParam(value = "direction", defaultValue = "ASC") Direction direction) {

        Page<ProductDto> productsPage = productService.find(filters, page, linesPerPage, sortBy, direction);

        return ResponseEntity.ok().headers(responseHeaders(productsPage)).body(productsPage.getContent());
    }
}
