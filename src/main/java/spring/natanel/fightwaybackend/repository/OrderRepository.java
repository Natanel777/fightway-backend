package spring.natanel.fightwaybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.natanel.fightwaybackend.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
