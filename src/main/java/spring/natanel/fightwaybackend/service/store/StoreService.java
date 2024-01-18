package spring.natanel.fightwaybackend.service.store;

import spring.natanel.fightwaybackend.dto.product.*;

import java.util.Collection;

public interface StoreService {

    ProductResponseDto addProduct(ProductRequestDto productRequestDto);

    Collection<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(long id);

    ProductResponseDto updateProductById(long id, UpdateProductRequestDto dto);

    //DELETE a post by id:
    DeleteProductResponseDto deleteProductById(long id);
    ProductPageResponseDto getPage(int pageNo, int pageSize, String sortBy, String sortDir,String type);
}
