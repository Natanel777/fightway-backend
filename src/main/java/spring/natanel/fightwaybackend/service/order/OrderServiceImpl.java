package spring.natanel.fightwaybackend.service.order;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import spring.natanel.fightwaybackend.dto.order.OrderRequestDto;
import spring.natanel.fightwaybackend.dto.order.OrderResponseDto;
import spring.natanel.fightwaybackend.dto.order.OrderStatusRequestDto;
import spring.natanel.fightwaybackend.entity.Customer;
import spring.natanel.fightwaybackend.entity.Order;
import spring.natanel.fightwaybackend.error.BadRequestException;
import spring.natanel.fightwaybackend.error.ResourceNotFoundException;
import spring.natanel.fightwaybackend.repository.CustomerRepository;
import spring.natanel.fightwaybackend.repository.OrderRepository;
import spring.natanel.fightwaybackend.repository.RoleRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;

    @Override
    public OrderResponseDto addOrder(OrderRequestDto orderRequestDto, Authentication authentication) {
        // Find customer
        var customer = findCustomerOrElseThrow(authentication);

        // Convert orderRequestDto to Order entity
        var order = modelMapper.map(orderRequestDto, Order.class);

        // Sum up prices of all items to calculate total amount
        var totalAmount = BigDecimal.ZERO;
        for (var orderItem : order.getItems()) {
            BigDecimal itemPrice = orderItem.getSubtotal().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalAmount = totalAmount.add(itemPrice);
            orderItem.setOrder(order);
        }

        //set other props
        order.setDate(LocalDate.now());
        order.setStatus("Pending");
        order.setCustomer(customer);
        order.setTotalPrice(totalAmount);

        // Save the entity
        Order savedOrder = orderRepository.save(order);

        // Convert the saved entity to OrderResponseDto
        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }

    @Override
    public List<OrderResponseDto> getOrdersById(Authentication authentication) {

        // Find customer
        var customer = findCustomerOrElseThrow(authentication);

        // Get customers orders
        var orders = orderRepository.findByCustomer_Id(customer.getId());

        // Convert the List<Order> to List<OrderResponseDto>
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    /* Admin Action */
    @Override
    public List<OrderResponseDto> getAllOrders(Authentication authentication) {

        // Make Sure It's Admin
        validateAdminOrElseThrow(authentication);

        // Implement the logic to get all orders
        var allOrders = orderRepository.findAll();

        // Convert the List<Order> to List<OrderResponseDto>
        return allOrders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    /* Admin Action */
    @Override
    public OrderResponseDto updateOrderStatusById(Long id, OrderStatusRequestDto statusDto, Authentication authentication) {

        // Make Sure It's Admin
        validateAdminOrElseThrow(authentication);

        // Get the order by ID or throw ResourceNotFoundException
        var order = orderRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("order", "id", id));

        // Check status value
        if (!(statusDto.getStatus().equalsIgnoreCase("pending") ||
                statusDto.getStatus().equalsIgnoreCase("approved") ||
                statusDto.getStatus().equalsIgnoreCase("declined")
        )){
            throw new BadRequestException("status","Status must be one of: Pending, Approved, Declined");
        }

        // Update the order status
        order.setStatus(statusDto.getStatus());

        // Save the changes
        Order savedOrder = orderRepository.save(order);

        // Convert the saved entity to OrderResponseDto
        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }


    private void validateAdminOrElseThrow(Authentication authentication) {
        //get customer
        var customer = findCustomerOrElseThrow(authentication);

        //get the admin role from database
        var adminRole = roleRepository.findByNameIgnoreCase("ROLE_ADMIN").orElseThrow();

        //check if the user role is admin
        if (!customer.getRoles().contains(adminRole)){
            throw new BadRequestException("customer","must be Admin in order to make this action");
        }
    }

    private Customer findCustomerOrElseThrow(Authentication authentication) {
        return customerRepository
                .findCustomerByUsernameIgnoreCase(authentication.getName()).orElseThrow();
    }
}
