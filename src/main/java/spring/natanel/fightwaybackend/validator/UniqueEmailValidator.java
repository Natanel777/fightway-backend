package spring.natanel.fightwaybackend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import spring.natanel.fightwaybackend.entity.Customer;
import spring.natanel.fightwaybackend.repository.CustomerRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Find a user with the given email (case-insensitive)
        Optional<Customer> user = customerRepository.findCustomerByEmailIgnoreCase(email);

        // If the user does not exist, the email is valid
        return user.isEmpty();
    }
}
