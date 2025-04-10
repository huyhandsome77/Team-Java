package uth.edu.jpa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "QuanLySan")
public class QLSModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id")
    private Long courtId;

    @Column (name="image")
    private String image;

    @Column(name = "court_name")
    private String courtName;

    @Column(name = "status")
    private String status;

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    @Column(name = "rental_price")
    private double rentalPrice;

    // Constructors
    public QLSModel() {}

    public QLSModel(String courtName, String status, double rentalPrice) {
        this.courtName = courtName;
        this.status = status;
        this.rentalPrice = rentalPrice;
    }

    // Getters and Setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
