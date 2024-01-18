package spring.natanel.fightwaybackend.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.validator.UniqueProductName;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {

    @Size(min = 4, max = 30)
    @NotNull
    @UniqueProductName
    private String name;

    @Size(min = 4, max = 300)
    @NotNull
    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "2000.0")
    @NotNull
    private BigDecimal price; // just like double or float without rounding issues

    @Size(min = 4, max = 30)
    @NotNull
    private String type;

    @NotNull
    private String category;

    @NotNull
    private String imageUrl;
}
