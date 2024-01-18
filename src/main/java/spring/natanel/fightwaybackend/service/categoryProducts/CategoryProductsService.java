package spring.natanel.fightwaybackend.service.categoryProducts;

import spring.natanel.fightwaybackend.dto.product.categoryProducts.CategoryProductsRequestDto;
import spring.natanel.fightwaybackend.dto.product.*;
import spring.natanel.fightwaybackend.dto.product.categoryProducts.UpdateCategoryProductRequestDto;

import java.util.Collection;

public interface CategoryProductsService {

    ProductResponseDto addProduct(String category, CategoryProductsRequestDto categoryProductsRequestDto);

    Collection<ProductResponseDto> getAllCategoryProducts(String category);

    ProductResponseDto getCategoryProductById(String category, long id);

    ProductResponseDto updateCategoryProductById(String category, long id, UpdateCategoryProductRequestDto dto);

    //DELETE a post by id:
    DeleteProductResponseDto deleteCategoryProductById(String category, long id);

    ProductPageResponseDto getPage(String category, int pageNo, int pageSize, String sortBy, String sortDir,String type);


}
