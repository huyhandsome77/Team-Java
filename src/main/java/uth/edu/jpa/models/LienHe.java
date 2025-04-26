package uth.edu.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LienHe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;
    private String email;
    private String sdt;
    private String tinNhan;

    private LocalDateTime thoiGianLienHe = LocalDateTime.now();

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getTinNhan() { return tinNhan; }
    public void setTinNhan(String tinNhan) { this.tinNhan = tinNhan; }

    public LocalDateTime getThoiGianLienHe() { return thoiGianLienHe; }
    public void setThoiGianLienHe(LocalDateTime thoiGianLienHe) { this.thoiGianLienHe = thoiGianLienHe; }
}
