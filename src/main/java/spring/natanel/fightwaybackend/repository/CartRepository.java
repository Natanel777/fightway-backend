package spring.natanel.fightwaybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.natanel.fightwaybackend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
