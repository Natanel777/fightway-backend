package spring.natanel.fightwaybackend.service.categoryProducts;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.natanel.fightwaybackend.dto.product.categoryProducts.CategoryProductsRequestDto;
import spring.natanel.fightwaybackend.dto.product.*;
import spring.natanel.fightwaybackend.dto.product.categoryProducts.UpdateCategoryProductRequestDto;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.entity.Product;
import spring.natanel.fightwaybackend.error.BadRequestException;
import spring.natanel.fightwaybackend.error.ResourceNotFoundException;
import spring.natanel.fightwaybackend.repository.CategoryRepository;
import spring.natanel.fightwaybackend.repository.ProductRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CategoryProductsServiceImpl implements CategoryProductsService {

    private final ModelMapper productModelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto addProduct(String category, CategoryProductsRequestDto categoryProductsRequestDto) {

        var entity = productModelMapper.map(categoryProductsRequestDto, Product.class);
        entity.setCategory(convertStringToCategoryEntity(category));

        Product savedProduct = productRepository.save(entity);


        return productModelMapper.map(savedProduct, ProductResponseDto.class);
    }

    @Override
    public Collection<ProductResponseDto> getAllCategoryProducts(String category) {
        var categoryEntity = convertStringToCategoryEntity(category);

        var getProductsCat = productRepository.findByCategory(categoryEntity);
        return getProductsCat.stream().map(p ->
                productModelMapper.map(p, ProductResponseDto.class)).toList();
    }

    @Override
    public ProductResponseDto getCategoryProductById(String category, long id) {
        return productModelMapper.map(
                getProductEntityByCategoryAndIdOrElseThrow(category, id), ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto updateCategoryProductById(String category, long id, UpdateCategoryProductRequestDto dto) {
        var product = getProductEntityByCategoryAndIdOrElseThrow(category, id);

        var categoryClass = convertStringToCategoryEntity(category);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setType(dto.getType());
        product.setCategory(categoryClass);
        product.setImageUrl(product.getImageUrl());

        var saved = productRepository.save(product);

        return productModelMapper.map(saved, ProductResponseDto.class);
    }

    @Override
    public DeleteProductResponseDto deleteCategoryProductById(String category, long id) {
        Category categoryEntity = convertStringToCategoryEntity(category);

        var product =  productRepository.findByCategoryAndId(categoryEntity, id);

        productRepository.deleteById(id);

        return DeleteProductResponseDto.builder()
                .deleted(product.isPresent())
                .post(productModelMapper.map(product, ProductResponseDto.class))
                .build();
    }

    @Override
    public ProductPageResponseDto getPage(String category, int pageNo, int pageSize, String sortBy, String sortDir,String type) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.fromString(sortDir), sortBy);

        Page<Product> page;

        //convert
        var categoryType = convertStringToCategoryEntity(category);

        if (type != null && !type.isEmpty()) {
            // If type is specified, filter by type
            page = productRepository.findByCategoryAndType(categoryType,type, pageable);
        } else {
            // If type is not specified, retrieve all products
            page = productRepository.findByCategory(categoryType, pageable);
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

    //finds the Category class that represent the provided string category
    //if not exists throw exception
    private Category convertStringToCategoryEntity(String category) {
        return categoryRepository.findByName(category.toLowerCase().trim())
                .orElseThrow(() -> new BadRequestException(category,"Invalid, \nthere is no such category"));
    }

    private Product getProductEntityByCategoryAndIdOrElseThrow(String category, long id) {
        Category categoryEntity = convertStringToCategoryEntity(category);

        return productRepository.findByCategoryAndId(categoryEntity, id).orElseThrow(
                () -> new ResourceNotFoundException("product", "id", id));
    }
}
