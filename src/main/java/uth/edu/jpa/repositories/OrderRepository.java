package uth.edu.jpa.repositories;

import uth.edu.jpa.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findTop10ByOrderByCreatedAtDesc();
}