package uth.edu.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lien_he_id")
    private LienHe lienHe;

    private String message;
    private String senderType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private boolean resolved;  // Đánh dấu đã giải quyết hay chưa

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LienHe getLienHe() {
        return lienHe;
    }

    public void setLienHe(LienHe lienHe) {
        this.lienHe = lienHe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
