package hr.algebra.dal.webshop2024dal.Repository;

import hr.algebra.dal.webshop2024dal.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUsername(String username);
    List<Order> findAllByUsername(String username);
    List<Order> findByUsernameAndPurchaseDateBetween(String username, LocalDateTime startDate, LocalDateTime endDate);
}