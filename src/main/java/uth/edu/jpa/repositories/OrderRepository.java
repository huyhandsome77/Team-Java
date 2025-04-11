package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Order;
import uth.edu.jpa.models.User;

import java.time.LocalDateTime;
import java.util.List;



public interface OrderRepository extends JpaRepository<Order, Long> {

}