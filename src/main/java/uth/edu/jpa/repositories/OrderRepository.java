package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Order;
import uth.edu.jpa.models.User;

import java.time.LocalDateTime;
import java.util.List;



public interface OrderRepository extends JpaRepository<Order, Long> {

    // Tìm đơn theo user
    List<Order> findByUser(User user);

    // Tìm đơn theo pitchId
    List<Order> findByPitchId(Long pitchId);

    // Tìm đơn trong khoảng thời gian
    List<Order> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    // Tìm đơn theo status
    List<Order> findByStatus(String status);

    // Tìm đơn theo user và status
    List<Order> findByUserAndStatus(User user, String status);
}