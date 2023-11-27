package spring.natanel.fightwaybackend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import spring.natanel.fightwaybackend.entity.Product;
import spring.natanel.fightwaybackend.repository.ProductRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ProductRepository postRepository;

    @Override
    public boolean isValid(String productName, ConstraintValidatorContext context) {
        Optional<Product> post = postRepository.findByProductName(productName);

        //if post with same title does not exist -> GOOD!
        return post.isEmpty();
    }
}
