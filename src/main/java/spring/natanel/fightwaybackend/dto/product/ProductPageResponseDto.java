package spring.natanel.fightwaybackend.dto.product;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageResponseDto {
    private List<ProductResponseDto> results;
    private  int totalPages;
    private long totalProducts;
    private boolean hasNext;
    /**
     * current page
     */
    private int pageNo;
    /**
     * current page size
     */
    private  int pageSize;
}
