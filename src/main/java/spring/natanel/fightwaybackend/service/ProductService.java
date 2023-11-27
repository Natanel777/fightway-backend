package spring.natanel.fightwaybackend.service;

import spring.natanel.fightwaybackend.dto.DeleteProductResponseDto;
import spring.natanel.fightwaybackend.dto.ProductRequestDto;
import spring.natanel.fightwaybackend.dto.ProductResponseDto;

import java.util.Collection;

public interface ProductService {

    ProductResponseDto addPost(ProductRequestDto postRequestDto);

    Collection<ProductResponseDto> getAll();

    ProductResponseDto getPostById(long id);

    ProductResponseDto updatePostById(long id, ProductRequestDto dto);

    //DELETE a post by id:
    DeleteProductResponseDto deletePostById(long id);

    Collection<ProductResponseDto> getPage(int pageNo, int pageSize);
}
