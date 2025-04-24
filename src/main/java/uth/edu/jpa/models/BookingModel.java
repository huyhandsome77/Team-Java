package uth.edu.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "booking")
public class BookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", nullable = false)  // JoinColumn to specify the foreign key column
    private QLSModel court;  // Thay thế courtId bằng court (QLSModel)

    // Constructors
    public BookingModel() {}

    public BookingModel(Long id, String fullName, String phoneNumber, LocalDate bookingDate,
                        LocalTime startTime, LocalTime endTime, String note, QLSModel court) {  // Sử dụng QLSModel thay vì courtId
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
        this.court = court;  // Sử dụng đối tượng court thay vì courtId
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
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
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public QLSModel getCourt() {  // Sử dụng getter cho court
        return court;
    }

    public void setCourt(QLSModel court) {  // Sử dụng setter cho court
        this.court = court;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bookingDate=" + bookingDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", note='" + note + '\'' +
                ", court=" + court +  // In ra đối tượng court
                '}';
    }
}
