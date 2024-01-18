package spring.natanel.fightwaybackend.dto.product.categoryProducts;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**product Dto without id and category*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCategoryProductRequestDto {

    @Size(min = 4, max = 30)
    @NotNull
    private String name;

    @Size(min = 4, max = 300)
    @NotNull
    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "2000.0")
    @NotNull
    private BigDecimal price;

    @Size(min = 4, max = 30)
    @NotNull
    private String type;

    @NotNull
    private String imageUrl;
}
