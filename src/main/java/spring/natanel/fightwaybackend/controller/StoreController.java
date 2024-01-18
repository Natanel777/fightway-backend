package spring.natanel.fightwaybackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import spring.natanel.fightwaybackend.dto.product.*;
import spring.natanel.fightwaybackend.service.store.StoreService;

import java.net.URI;
import java.util.Collection;

/**in this controller we will see all the Products in this store*/
@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService productService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto dto, UriComponentsBuilder uriBuilder){
        //service -> save(dto)
        var saved = productService.addProduct(dto); // we converted the request to post so we get PostResponseDto

        //created url:
        //1. one way without the builder:
        var uri = URI.create("/api/v1/store/" + saved.getId());

        //2.(recommended) second way with the builder: (spring will inject the builder automatically in props)
        var uri2 = uriBuilder.path("/api/v1/store/{id}").buildAndExpand(saved.getId()).toUri();
        //response.created
        return ResponseEntity.created(uri2).body(saved);
    }

    @GetMapping
    @Operation(summary = "Get All The Products")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductRequestDto.class)
                            ))),

            @ApiResponse(
                    responseCode = "401",
                    content = @Content(mediaType = "application/json"),
                    description = "Unauthorized")
    })
    public ResponseEntity<Collection<ProductResponseDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
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
    public ResponseEntity<ProductResponseDto> getProdcutById(
            @PathVariable @NotNull @Parameter(description = "id of product to be searched") Long id){
        return ResponseEntity.ok(productService.getProductById(id));

    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')") //Spring knows to search ROLE_ and the rest in User
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid UpdateProductRequestDto dto ){
        return ResponseEntity.ok(productService.updateProductById(id, dto));
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") //Spring knows to search ROLE_ and the rest in User that provided in Authentication
    public ResponseEntity<DeleteProductResponseDto> deleteProductById(@PathVariable @NotNull Long id) {

        return ResponseEntity.ok(productService.deleteProductById(id));
    }

    @GetMapping("/page")
    public ResponseEntity<ProductPageResponseDto> getPage(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(productService.getPage(pageNo,pageSize, sortBy, sortDir,type));
    }
}


