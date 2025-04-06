package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Order;

import uth.edu.jpa.services.OrderService;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // API lấy tất cả đơn hàng
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // API lấy đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // API thêm đơn hàng mới
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    // API xóa đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @GetMapping("/nhacnho")
//    public String getAppointmentsForUser(Model model) {
//        // Giả sử bạn đã có cách lấy danh sách các đơn hàng của người dùng từ Service
//        List<Order> orders = orderService.getOrdersByUserId(currentUserId); // Cần lấy ID người dùng hiện tại
//        model.addAttribute("appointments", orders);
//        return "appointments"; // Tên view (HTML)
//    }
}


