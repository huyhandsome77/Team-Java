package uth.edu.jpa.models;
import jakarta.persistence.*;
import uth.edu.jpa.models.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pitch_orders")
public class PitchOrder extends Order {

    @Column(name = "pitch_id")
    private Long pitchId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    // Constructor
    public PitchOrder() {}

    public PitchOrder(User user, LocalDateTime orderDate, double price, String status, Long pitchId, LocalDateTime startTime, LocalDateTime endTime) {
        super(user, orderDate, price, status);
        this.pitchId = pitchId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getPitchId() { return pitchId; }
    public void setPitchId(Long pitchId) { this.pitchId = pitchId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "PitchOrder{" +
                "pitchId=" + pitchId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "} " + super.toString();
    }
}
