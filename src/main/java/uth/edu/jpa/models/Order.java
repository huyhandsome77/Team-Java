package uth.edu.jpa.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // It's good practice to specify the table name
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalAmount;
    private LocalDateTime createdAt;
    private String bankCode;
    private String payDate;
    private OrderStatus status;

    public enum OrderStatus {
        PENDING, // Đơn hàng mới được thanh toán thành công, đang chờ xử lý
        COMPLETED, // Đơn hàng đã hoàn thành
        CANCELLED, // Đơn hàng đã bị huỷ
        FAILED; // Đơn hàng thanh toán thất bại
    }



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress deliveryAddress;

    @ManyToOne // Assuming many orders can belong to one user
    @JoinColumn(name = "user_id") // Foreign key column in the orders table
    private User user;

    // Getters & setters
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}