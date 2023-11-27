package spring.natanel.fightwaybackend.repository;

import spring.natanel.fightwaybackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);
}