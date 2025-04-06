package uth.edu.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID")
    private User user;  // Sử dụng User thay cho customerId

    @Column(name = "pitch_id")
    private Long pitchId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private double price;

    private String status;

    // Constructors, getters, setters, toString()
    public Order() {}

    public Order(User user, Long pitchId, LocalDateTime startTime, LocalDateTime endTime, double price, String status) {
        this.user = user;
        this.pitchId = pitchId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.status = status;
    }

    // Getters và setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPitchId() {
        return pitchId;
    }

    public void setPitchId(Long pitchId) {
        this.pitchId = pitchId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", pitchId=" + pitchId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
