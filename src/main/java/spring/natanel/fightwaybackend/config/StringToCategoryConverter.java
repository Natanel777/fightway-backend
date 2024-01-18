package spring.natanel.fightwaybackend.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.error.ResourceNotFoundException;
import spring.natanel.fightwaybackend.repository.CategoryRepository;

@Component
@RequiredArgsConstructor
public class StringToCategoryConverter extends AbstractConverter<String, Category> {

    private final CategoryRepository categoryRepository;

    @Override
    protected Category convert(String categoryName) {
        // Check if a category with the given name already exists
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Name", categoryName));
    }
}