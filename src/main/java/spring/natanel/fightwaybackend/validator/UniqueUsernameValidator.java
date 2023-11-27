package spring.natanel.fightwaybackend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import spring.natanel.fightwaybackend.entity.Customer;
import spring.natanel.fightwaybackend.repository.CustomerRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // Find a user with the given username (case-insensitive)
        Optional<Customer> user = customerRepository.findCustomerByUsernameIgnoreCase(username);

        // If the user does not exist, the username is valid
        return user.isEmpty();
    }
}
