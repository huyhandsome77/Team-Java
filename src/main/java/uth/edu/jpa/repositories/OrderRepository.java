package uth.edu.jpa.repositories;

import uth.edu.jpa.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}