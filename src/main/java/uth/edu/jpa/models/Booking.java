package uth.edu.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court; // ✅ Sửa: từ Long -> Court

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Booking() {}

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        // Tự động tính endTime nếu chưa có
        if (endTime == null && this.startTime != null) {
            this.endTime = this.startTime.plusHours(1);
        } else {
            this.endTime = endTime;
        }
    }
}
