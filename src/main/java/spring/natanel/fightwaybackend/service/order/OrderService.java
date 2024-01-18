package spring.natanel.fightwaybackend.service.order;

import org.springframework.security.core.Authentication;
import spring.natanel.fightwaybackend.dto.order.OrderRequestDto;
import spring.natanel.fightwaybackend.dto.order.OrderResponseDto;
import spring.natanel.fightwaybackend.dto.order.OrderStatusRequestDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto addOrder(OrderRequestDto orderRequestDto, Authentication authentication);

    List<OrderResponseDto> getOrdersById(Authentication authentication);

    List<OrderResponseDto> getAllOrders(Authentication authentication);

    OrderResponseDto updateOrderStatusById(Long id, OrderStatusRequestDto statusDto, Authentication authentication);
}
