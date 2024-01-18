package spring.natanel.fightwaybackend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;

    private List<OrderItemResponseDto> items;

    private LocalDate date;

    private BigDecimal totalPrice;

    private String paymentInformation;

    private String address;

    private String city;

    private String phoneNumber;

    private String postalCode;

    private String status;
}
