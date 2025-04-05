package uth.edu.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="supports")
public class SupportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long supportid;

    private String name;

    public long getSupportid() {
        return supportid;
    }

    public void setSupportid(int supportid) {
        this.supportid = supportid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    private String email;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;
    private LocalDateTime createdAt;
    private boolean resolved;
}

