package br.com.zitrus.desafio.service.impl.product;

import br.com.zitrus.desafio.domain.product.Product;
import br.com.zitrus.desafio.exception.BadRequestException;
import br.com.zitrus.desafio.exception.InternalServerErrorException;
import br.com.zitrus.desafio.repository.product.ProductRepository;
import br.com.zitrus.desafio.repository.product.criteria.pojo.ProductFilter;
import br.com.zitrus.desafio.service.enums.Direction;
import br.com.zitrus.desafio.service.interfaces.CrudInterface;
import br.com.zitrus.desafio.service.mapper.ProductMapper;
import br.com.zitrus.desafio.service.pojo.dto.product.ProductDto;
import br.com.zitrus.desafio.service.pojo.form.product.ProductForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements CrudInterface<ProductDto, ProductForm, ProductFilter> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto save(ProductForm form, Long id) {
        productAlreadyExists(form.getName());

        productNotFound(id);

        return mapperProduct(save(mapperProduct(form, id)));
    }

    @Override
    public void delete(Long id) {
        productNotFound(id);

        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível salvar!");
        }
    }

    @Override
    public Page<ProductDto> find(ProductFilter productFilter,
                                 Integer page,
                                 Integer linesPerPage,
                                 String sortBy,
                                 Direction direction) {
        try {
            Pageable pageable = PageRequest.of(page, linesPerPage, Sort.by(sortBy));

            Page<ProductDto> productsPage = productRepository.filterProduct(productFilter, pageable, direction);

            if (productsPage.isEmpty()) {
                return null;
            }

            return productsPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Não foi possível retornar a lista de produtos!");
        }
    }

    private void productAlreadyExists(String nameProduct) {
        if (productRepository.existsByName(nameProduct)) {
            throw new BadRequestException("Produto já cadastrada em nossa base de dados!");
        }
    }

    private void productNotFound(Long id) {
        if (!isNull(id)) {
            Optional<Product> product = productRepository.findById(id);

            if (!product.isPresent()) {
                throw new BadRequestException("Produto não localizado em nossa base de dados!");
            }
        }
    }

    private Product save(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível salvar!");
        }
    }

    private Product mapperProduct(ProductForm form, Long id) {
        try {
            Product product;

            product = productMapper.toEntity(form);

            if (isNull(id)) {
                product.setCreatedAt(LocalDateTime.now());
            }

            if (!isNull(id)) {
                Optional<Product> productOptional = productRepository.findById(id);

                product.setIdProduct(id);
                product.setCreatedAt(productOptional.get().getCreatedAt());
                product.setUpdatedAt(LocalDateTime.now());
            }

            return product;
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para entidade!");
        }
    }

    private ProductDto mapperProduct(Product product) {
        try {
            return productMapper.toDto(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Não foi possível realizar o Mapper para DTO!");
        }
    }
}
