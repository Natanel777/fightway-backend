package spring.natanel.fightwaybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spring.natanel.fightwaybackend.dto.product.DeleteProductResponseDto;
import spring.natanel.fightwaybackend.dto.product.ProductPageResponseDto;
import spring.natanel.fightwaybackend.dto.product.ProductResponseDto;
import spring.natanel.fightwaybackend.dto.product.categoryProducts.CategoryProductsRequestDto;
import spring.natanel.fightwaybackend.dto.product.categoryProducts.UpdateCategoryProductRequestDto;
import spring.natanel.fightwaybackend.service.categoryProducts.CategoryProductsService;

import java.util.Collection;

/**in this controller we will see all the Category name(except MMA that contains all the product)*/
@RestController
@RequestMapping("/api/v1/{category}")
@RequiredArgsConstructor
public class CategoryProductsController {

    private final CategoryProductsService categoryProductService;


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> addProduct(
            @RequestBody @Valid CategoryProductsRequestDto dto,
            @PathVariable String category,
            UriComponentsBuilder uriBuilder){

        var saved = categoryProductService.addProduct(category, dto);
        var uri2 = uriBuilder.path("/api/v1/{category}/{id}").buildAndExpand(category, saved.getId()).toUri();

        return ResponseEntity.created(uri2).body(saved);
    }

    @GetMapping
    @Operation(summary = "Get All The Category Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid category supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    public ResponseEntity<Collection<ProductResponseDto>> getAllCategoryProducts(
            @PathVariable @NotNull @Parameter(description = "category of product to be searched") String category){
        Collection<ProductResponseDto> products = categoryProductService.getAllCategoryProducts(category);
        return ResponseEntity.ok(products);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    public ResponseEntity<ProductResponseDto> getCategoryProductById(
            @PathVariable @NotNull Long id, @PathVariable @NotNull String category){
        return ResponseEntity.ok(categoryProductService.getCategoryProductById(category,id));

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')") //Spring knows to search ROLE_ and the rest in User
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(
            @PathVariable @NotNull Long id,
            @PathVariable @NotNull String category,
            @RequestBody @Valid UpdateCategoryProductRequestDto dto ){
        return ResponseEntity.ok(categoryProductService.updateCategoryProductById(category,id, dto));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") //Spring knows to search ROLE_ and the rest in User that provided in Authentication
    public ResponseEntity<DeleteProductResponseDto> deleteProductById(
            @PathVariable @NotNull Long id,
            @PathVariable @NotNull String category) {

        return ResponseEntity.ok(categoryProductService.deleteCategoryProductById(category,id));
    }

    @GetMapping("/page")
    public ResponseEntity<ProductPageResponseDto> getPage(
            @PathVariable @NotNull String category,
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(categoryProductService.getPage(category, pageNo,pageSize, sortBy, sortDir,type));
    }
}
