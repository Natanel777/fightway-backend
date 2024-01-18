package spring.natanel.fightwaybackend.dto.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteProductResponseDto {
    private boolean deleted;
    private ProductResponseDto post;
}
