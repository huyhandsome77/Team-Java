package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Order;
import uth.edu.jpa.services.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String getRecentOrders(Model model) {
        // Lấy danh sách đơn hàng gần đây
        model.addAttribute("orders", orderService.getRecentOrders());
        return "orders";
    }

    @PostMapping("/orders/change-status")
    public String changeOrderStatus(@RequestParam Long orderId, @RequestParam String status, Model model) {
        try {
            // Lấy đơn hàng theo ID
            Order order = orderService.getOrderById(orderId);
            if (order == null) {
                model.addAttribute("error", "Không tìm thấy đơn hàng.");
                return "redirect:/admin/quanlibanhang"; // Chuyển hướng về danh sách nếu không tìm thấy đơn hàng
            }

            // Kiểm tra giá trị status có hợp lệ hay không
            try {
                Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status);
                order.setStatus(orderStatus); // Thay đổi trạng thái
                orderService.saveOrder(order); // Lưu lại thay đổi

                // Thêm thông báo thành công vào model
                model.addAttribute("message", "Trạng thái đơn hàng đã được cập nhật!");
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", "Trạng thái không hợp lệ.");
            }

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi cập nhật trạng thái.");
        }

        return "redirect:/admin/quanlibanhang"; // Chuyển về trang danh sách đơn hàng
    }
}
