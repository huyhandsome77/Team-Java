package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Order;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public double getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getTotalAmount();
        }
        return totalRevenue;
    }


    // Lấy đơn hàng theo ID
//    public Optional<Order> getOrderById(Long id) {
//        return orderRepository.findById(id);
//    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Lưu đơn hàng
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Xóa đơn hàng
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    public List<Order> getRecentOrders() {
        return orderRepository.findTop10ByOrderByCreatedAtDesc();
    }
    public List<Order> getOrderHistory(User user) {
        return orderRepository.findByUser(user);
    }
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }



}
