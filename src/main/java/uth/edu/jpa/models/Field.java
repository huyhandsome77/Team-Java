package uth.edu.jpa.models;
import jakarta.persistence.*;
@Entity
@Table (name="sanbong")
public class sanbong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String location;
    private Double pricePerHour;
    private Boolean isAvailable;
    public sanbong() {}
    public sanbong(String name, String location, Double pricePerHour, Boolean isAvailable) {
        this.name = name;
        this.location = location;
        this.pricePerHour = pricePerHour;
        this.isAvailable = isAvailable;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "sanbong{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

