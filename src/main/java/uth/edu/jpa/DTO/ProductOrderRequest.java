package uth.edu.jpa.DTO;
import java.util.List;

public class ProductOrderRequest {
    private Long userId;
    private List<Long> productIds;
    private double totalPrice;

    private String fullName;
    private String phone;
    private String address;
    private String note;

    // Getters và Setters
}
