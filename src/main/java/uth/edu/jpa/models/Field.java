package uth.edu.jpa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "fields") // Đổi thành chữ thường cho chuẩn SQL
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private double pricePerHour;
    private boolean available;

    // Constructors
    public Field() {}

    public Field(String name, String location, double pricePerHour, boolean available) {
        this.name = name;
        this.location = location;
        this.pricePerHour = pricePerHour;
        this.available = available;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
