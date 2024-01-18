package spring.natanel.fightwaybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String productName);

    List<Product> findByCategory(Category category);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByType(String type, Pageable pageable);

    Page<Product> findByCategoryAndType(Category category,String type, Pageable pageable);

    Optional<Product> findByCategoryAndId(Category category, long id);
}
