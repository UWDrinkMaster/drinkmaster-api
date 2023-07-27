package ca.uwaterloo.drinkmasterapi.feature.order.repository;

import ca.uwaterloo.drinkmasterapi.dao.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
