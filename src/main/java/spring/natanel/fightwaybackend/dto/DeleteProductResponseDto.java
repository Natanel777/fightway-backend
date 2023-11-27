package spring.natanel.fightwaybackend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteProductResponseDto {
    private boolean deleted;
    private ProductRequestDto post;
}
