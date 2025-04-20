package uth.edu.jpa.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.models.DeliveryAddress;
import uth.edu.jpa.models.Order;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.DeliveryAddressRepository;
import uth.edu.jpa.repositories.OrderRepository;
import uth.edu.jpa.services.CartService;
import uth.edu.jpa.config.SecurityUtils;
import uth.edu.jpa.services.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private VnPayService vnPayService;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private OrderService orderService;

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long sanPhamId) {
        System.out.println("Adding product with ID: " + sanPhamId);
        User user = SecurityUtils.getCurrentUser();
        cartService.addToCart(user, sanPhamId);
        return "redirect:/cart";
    }

    /**
     * Hiển thị giỏ hàng của người dùng
     */
    @GetMapping
    public String viewCart(Model model) {
        User user = SecurityUtils.getCurrentUser();
        model.addAttribute("cartItems", cartService.getCartItems(user));
        model.addAttribute("total", cartService.getTotal(user));
        return "/player/Player_GioHang";
    }

    /**
     * Xoá sản phẩm khỏi giỏ
     */
    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long sanPhamId) {
        User user = SecurityUtils.getCurrentUser();
        cartService.removeFromCart(user, sanPhamId);
        return "redirect:/cart";
    }

    /**
     * Cập nhật số lượng sản phẩm
     */
    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable("id") Long sanPhamId,
                                 @RequestParam("quantity") int quantity) {
        User user = SecurityUtils.getCurrentUser();
        cartService.updateQuantity(user, sanPhamId, quantity);
        return "redirect:/cart";
    }
    @PostMapping("/checkout")
    public String handleCheckout(@RequestParam("fullname") String fullName,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("address") String address,
                                 @RequestParam(value = "note", required = false) String note,
                                 HttpSession session,
                                 HttpServletRequest request) {

        // Lưu địa chỉ giao hàng vào DB
        DeliveryAddress delivery = new DeliveryAddress(fullName, phone, address, note);
        delivery = deliveryAddressRepository.save(delivery);
        session.setAttribute("deliveryAddressId", delivery.getId());

        // Tính tổng tiền
        User user = SecurityUtils.getCurrentUser();
        double totalAmount = cartService.getTotal(user);
        session.setAttribute("total", totalAmount);

        // Tạo URL và redirect
        String paymentUrl = createVnpayPaymentRequest(request, totalAmount, delivery.getId());
        return "redirect:" + paymentUrl;
    }
    public String createVnpayPaymentRequest(HttpServletRequest request, double amount, Long deliveryAddressId) {
        try {
            String vnp_TxnRef = UUID.randomUUID().toString().substring(0, 8);
            String vnp_IpAddr = request.getRemoteAddr();

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cal.getTime());
            cal.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cal.getTime());

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", "2.1.0");
            vnp_Params.put("vnp_Command", "pay");
            vnp_Params.put("vnp_TmnCode", uth.edu.jpa.config.Config.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf((long) (amount * 100000)));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + deliveryAddressId);
            vnp_Params.put("vnp_OrderType", "other");
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", uth.edu.jpa.config.Config.vnp_Returnurl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            for (int i = 0; i < fieldNames.size(); i++) {
                String field = fieldNames.get(i);
                String value = vnp_Params.get(field);
                hashData.append(field).append("=").append(URLEncoder.encode(value, "UTF-8"));
                query.append(URLEncoder.encode(field, "UTF-8")).append("=")
                        .append(URLEncoder.encode(value, "UTF-8"));
                if (i < fieldNames.size() - 1) {
                    hashData.append("&");
                    query.append("&");
                }
            }

            String secureHash = uth.edu.jpa.config.Config.hmacSHA512(uth.edu.jpa.config.Config.vnp_HashSecret, hashData.toString());
            query.append("&vnp_SecureHash=").append(secureHash);

            return uth.edu.jpa.config.Config.vnp_PayUrl + "?" + query.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Lỗi mã hóa URL", e);
        }
    }


//    @GetMapping("/payment/vnpay_return")
//    public String vnpayReturn(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
//        // Lấy các tham số trả về từ VNPAY
//        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
//        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
//        String vnp_Amount = request.getParameter("vnp_Amount");
//
//        // Kiểm tra mã phản hồi của giao dịch
//        if ("00".equals(vnp_ResponseCode)) {
//            // Thanh toán thành công
//            // Lấy thông tin đơn hàng, ví dụ từ session
//            Long deliveryId = (Long) session.getAttribute("deliveryAddressId");
//            double total = (double) session.getAttribute("total");
//            User user = SecurityUtils.getCurrentUser();
//
//            // Lưu đơn hàng vào DB
//            Order order = new Order();
//            order.setUser(user);
//            order.setTotalAmount(Math.round(total));  // Đảm bảo số tiền được làm tròn
//            DeliveryAddress delivery = deliveryAddressRepository.findById(deliveryId).orElse(null);
//            if (delivery == null) {
//                redirectAttributes.addFlashAttribute("error", "Không tìm thấy địa chỉ giao hàng.");
//                return "redirect:/cart";
//            }
//            order.setDeliveryAddress(delivery);
//            order.setBankCode(vnp_TxnRef);  // Lưu mã giao dịch VNPAY
//            order.setStatus(Order.OrderStatus.PENDING);  // Trạng thái thanh toán chưa xử lý (có thể là "PENDING" hoặc "COMPLETED")
//
//            // Lưu đơn hàng vào cơ sở dữ liệu
//            orderService.saveOrder(order);
//
//            // Xoá giỏ hàng
//            cartService.clearCart(user);
//
//            // Thông báo thành công
//            redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
//        } else {
//            // Thanh toán thất bại
//            redirectAttributes.addFlashAttribute("message", "Thanh toán thất bại! Vui lòng thử lại.");
//        }
//
//        // Chuyển hướng đến trang kết quả thanh toán
//        return "redirect:/payment/payment-result";  // Điều hướng tới trang kết quả thanh toán
//    }




}
