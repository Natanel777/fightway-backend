package spring.natanel.fightwaybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.natanel.fightwaybackend.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCustomer_Id(Long customerId);
}
