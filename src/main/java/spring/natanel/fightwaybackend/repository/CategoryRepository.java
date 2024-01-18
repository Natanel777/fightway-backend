package spring.natanel.fightwaybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.entity.Product;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Optional<Category> findByName(String name);
}
