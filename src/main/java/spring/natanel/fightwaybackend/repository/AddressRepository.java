package spring.natanel.fightwaybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.natanel.fightwaybackend.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
