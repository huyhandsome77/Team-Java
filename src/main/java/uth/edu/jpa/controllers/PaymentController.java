package uth.edu.jpa.controllers;


import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.config.*;
import uth.edu.jpa.models.*;
import uth.edu.jpa.repositories.DeliveryAddressRepository;

import uth.edu.jpa.services.CartService;
import uth.edu.jpa.services.OrderService;
import uth.edu.jpa.services.VnPayService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PaymentController {
    @Autowired
    private CartService cartService;

    @Autowired
    private VnPayService vnPayService;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/payment/payment-result")
    public String resultPage() {
        return "payment-result"; // Tên file Thymeleaf: payment-result.html
    }





    @GetMapping("/payment/vnpay_return")
    public String vnpayReturn(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        Logger logger = LoggerFactory.getLogger(getClass());

        try {
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");

            // Lấy dữ liệu từ session
            Long deliveryId = (Long) session.getAttribute("deliveryAddressId");
            Double total = (Double) session.getAttribute("total");

            if (deliveryId == null || total == null) {
                redirectAttributes.addFlashAttribute("error", "Session đã hết hạn. Vui lòng thử lại.");
                return "redirect:/cart";
            }

            if ("00".equals(vnp_ResponseCode)) {
                User user = SecurityUtils.getCurrentUser();
                if (user == null) {
                    redirectAttributes.addFlashAttribute("error", "Bạn chưa đăng nhập.");
                    return "redirect:/login";
                }

                DeliveryAddress delivery = deliveryAddressRepository.findById(deliveryId).orElse(null);
                if (delivery == null) {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy địa chỉ giao hàng.");
                    return "redirect:/cart";
                }

                // Tạo và lưu đơn hàng
                Order order = new Order();
                order.setUser(user);
                order.setTotalAmount(Math.round(total));
                order.setDeliveryAddress(delivery);
                order.setBankCode(vnp_TxnRef);
                order.setStatus(Order.OrderStatus.PENDING);
                orderService.saveOrder(order);

                // Xóa giỏ hàng và session
                cartService.clearCart(user);
                session.removeAttribute("deliveryAddressId");
                session.removeAttribute("total");

                redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Thanh toán thất bại! Vui lòng thử lại.");
            }

            return "redirect:/payment/payment-result";

        } catch (Exception e) {
            logger.error("Lỗi xử lý callback VNPAY: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi xử lý thanh toán.");
            return "redirect:/cart";
        }
    }

}
