package uth.edu.jpa.config;

public class VnpayConfig {
    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "pay";
    public static String vnp_TmnCode = "WDZS6YNB"; // Mã website bạn đăng ký với VNPAY
    public static String vnp_HashSecret = "IGLEQFKOO8EU91I1QM13XZI2D09O0EZP"; // Chuỗi ký bí mật
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"; // URL sandbox
    public static String vnp_ReturnUrl = "http://localhost:8080/payment/return"; // Khi thanh toán xong sẽ redirect về đây
}