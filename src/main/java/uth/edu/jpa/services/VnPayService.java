package uth.edu.jpa.services;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class VnPayService {

    private final String vnpayUrl = "https://sandbox.vnpayment.vn/merchant_webapi/checkout.php";
    private final String vnpayHashSecret = "IGLEQFKOO8EU91I1QM13XZI2D09O0EZP"; // Secret Key của bạn
    private final String vnpayMerchantCode = "WDZS6YNB"; // Mã merchant của bạn
    private final String vnpayReturnUrl = "http://localhost:8080/payment/result"; // URL trả về sau khi thanh toán

    // Tạo yêu cầu thanh toán VNPAY
    public String createPaymentRequest(String orderId, double amount, String returnUrl) {
        try {
            Map<String, String> vnpParams = new HashMap<>();

            vnpParams.put("vnp_Version", "2.0.0");
            vnpParams.put("vnp_Command", "pay");
            vnpParams.put("vnp_TmnCode", vnpayMerchantCode);
            vnpParams.put("vnp_Amount", String.valueOf(amount * 100)); // Số tiền (VNĐ)
            vnpParams.put("vnp_CurrCode", "VND");
            vnpParams.put("vnp_TxnRef", orderId); // Mã đơn hàng
            vnpParams.put("vnp_OrderInfo", "Thanh toán giỏ hàng");
            vnpParams.put("vnp_ReturnUrl", returnUrl); // URL trả về sau khi thanh toán

            // Tạo HMAC-SHA256 signature
            String hashData = createHashData(vnpParams);
            String vnpSecureHash = encryptHMACSHA256(hashData, vnpayHashSecret);

            vnpParams.put("vnp_SecureHash", vnpSecureHash); // Thêm signature vào request

            // Tạo URL yêu cầu thanh toán
            StringBuilder urlBuilder = new StringBuilder(vnpayUrl);
            if (vnpParams != null && vnpParams.size() > 0) {
                urlBuilder.append("?");
                Set<String> keySet = vnpParams.keySet();
                for (String key : keySet) {
                    String value = vnpParams.get(key);
                    urlBuilder.append(key).append("=").append(value).append("&");
                }
                urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Loại bỏ dấu "&" cuối cùng
            }
            return urlBuilder.toString(); // Trả về URL thanh toán
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức tạo hash data từ các tham số
    private String createHashData(Map<String, String> params) {
        StringBuilder hashData = new StringBuilder();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            hashData.append(key).append("=").append(value).append("&");
        }
        return hashData.toString();
    }

    // Phương thức mã hóa HMAC-SHA256
    private String encryptHMACSHA256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(data.getBytes());
            return bytesToHex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}