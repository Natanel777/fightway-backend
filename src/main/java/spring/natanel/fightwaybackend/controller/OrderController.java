package spring.natanel.fightwaybackend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spring.natanel.fightwaybackend.dto.order.OrderRequestDto;
import spring.natanel.fightwaybackend.dto.order.OrderResponseDto;
import spring.natanel.fightwaybackend.dto.order.OrderStatusRequestDto;
import spring.natanel.fightwaybackend.service.order.OrderService;


import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> addOrder(
            @RequestBody @Valid OrderRequestDto dto,
            UriComponentsBuilder uriBuilder,
            Authentication authentication
    ){
        var saved = orderService.addOrder(dto, authentication);
        var uri = uriBuilder
                .path("/api/v1/order/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping("/recentorders")
    public ResponseEntity<List<OrderResponseDto>> getOrdersById(Authentication authentication){
        return ResponseEntity.ok(orderService.getOrdersById(authentication));
    };


    @GetMapping("/ordermanager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(Authentication authentication){
        return ResponseEntity.ok(orderService.getAllOrders(authentication));
    };

    @PutMapping("/ordermanager/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updatePostById(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody OrderStatusRequestDto statusDto,
            Authentication authentication){
        return ResponseEntity.ok(orderService.updateOrderStatusById(id, statusDto,authentication));
    }

}
