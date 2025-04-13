package uth.edu.jpa.models;
import jakarta.persistence.*;
import uth.edu.jpa.models.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_orders")
public class ProductOrder extends Order {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    private Delivery delivery;

    // Constructor
    public ProductOrder() {}

    public ProductOrder(User user, LocalDateTime orderDate, double price, String status, Delivery delivery) {
        super(user, orderDate, price, status);
        this.delivery = delivery;
    }

    public Delivery getDelivery() { return delivery; }
    public void setDelivery(Delivery delivery) { this.delivery = delivery; }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "delivery=" + delivery +
                "} " + super.toString();
    }
}
