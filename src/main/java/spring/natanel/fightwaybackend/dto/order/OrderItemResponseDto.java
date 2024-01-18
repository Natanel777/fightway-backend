package spring.natanel.fightwaybackend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.natanel.fightwaybackend.entity.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDto {

    private Long id;

    private Product product;

    private int quantity;

    private BigDecimal totalAmount;


//    private String productName;
//
//    private BigDecimal price;

}
