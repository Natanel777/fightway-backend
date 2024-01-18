package spring.natanel.fightwaybackend.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.natanel.fightwaybackend.entity.OrderItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    private List<OrderItemRequestDto> items;

    @NotNull
    @Size(min = 1, max = 15, message = "Payment information must be between 1 and 15 characters")
    private String paymentInformation;

    @NotNull
    @Size(min = 3, max = 50, message = "Address must be between 3 and 50 characters")
    private String address;

    @NotNull
    @Size(min = 3, max = 58, message = "City must be between 3 and 58 characters")
    private String city;

    @NotNull
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "\\d{7}", message = "Postal code must be 7 digits")
    private String postalCode;

}
