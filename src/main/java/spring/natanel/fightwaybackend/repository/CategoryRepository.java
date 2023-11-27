package spring.natanel.fightwaybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.entity.Product;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
