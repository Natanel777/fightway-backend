package spring.natanel.fightwaybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.natanel.fightwaybackend.entity.Customer;


import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //DERIVED QUERY METHODS:

    Optional<Customer> findCustomerByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
    Optional<Customer> findCustomerByUsernameIgnoreCase(String username);
    Optional<Customer> findCustomerByEmailIgnoreCase(String email);

}
