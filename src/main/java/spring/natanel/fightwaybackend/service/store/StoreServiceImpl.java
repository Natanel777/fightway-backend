package spring.natanel.fightwaybackend.service.store;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.natanel.fightwaybackend.dto.product.*;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.entity.Product;
import spring.natanel.fightwaybackend.error.ResourceNotFoundException;
import spring.natanel.fightwaybackend.repository.CategoryRepository;
import spring.natanel.fightwaybackend.repository.ProductRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {


    private final ModelMapper productModelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {

        var entity = productModelMapper.map(productRequestDto, Product.class);

        Product savedProduct = productRepository.save(entity);


        return productModelMapper.map(savedProduct, ProductResponseDto.class);
    }

    @Override
    public Collection<ProductResponseDto> getAllProducts() {
        var getAll = productRepository.findAll();
        
        return getAll.stream().map(mmp -> productModelMapper.map(mmp, ProductResponseDto.class)).toList();
    }

    @Override
    public ProductResponseDto getProductById(long id) {

        return productModelMapper.map(
                getProductEntityByIdOrElseThrow(id), ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto updateProductById(long id, @Valid UpdateProductRequestDto dto) {
        var product = getProductEntityByIdOrElseThrow(id);

        var categoryEntity = categoryRepository.findByName(dto.getCategory()).orElseThrow();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setType(dto.getType());
        product.setCategory(categoryEntity);
        product.setImageUrl(product.getImageUrl());

        var saved = productRepository.save(product);

        return productModelMapper.map(saved, ProductResponseDto.class);

    }

    @Override
    public DeleteProductResponseDto deleteProductById(long id) {
        var product =  productRepository.findById(id);

        productRepository.deleteById(id);

        return DeleteProductResponseDto.builder()
                .deleted(product.isPresent())
                .post(productModelMapper.map(product, ProductResponseDto.class))
                .build();
    }


    public ProductPageResponseDto getPage(int pageNo, int pageSize, String sortBy, String sortDir,String type) {

        //if sorting is category(Object) sorting by category name
        if (sortBy.equalsIgnoreCase(Category.class.getSimpleName())){
            sortBy = String.join(".", sortBy, "name");
        }

        //page request: defines the page size and page number:
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.fromString(sortDir), sortBy);

        Page<Product> page;

        if (type != null && !type.isEmpty()) {
            // If type is specified, filter by type
            page = productRepository.findByType(type, pageable);
        } else {
            // If type is not specified, retrieve all products
            page = productRepository.findAll(pageable);
        }

        return ProductPageResponseDto.builder()
                .results(page.stream().map(product -> productModelMapper.map(product, ProductResponseDto.class)).toList())
                .totalPages(page.getTotalPages())
                .totalProducts(page.getTotalElements())
                .hasNext(page.hasNext())
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .build();
    }

    private Product getProductEntityByIdOrElseThrow(long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("product", "id", id));
    }
}
