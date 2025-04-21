package uth.edu.jpa.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    private int soSao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSoSao() {
        return soSao;
    }

    public void setSoSao(int soSao) {
        this.soSao = soSao;
    }

    public QLSModel getCourt() {
        return court;
    }

    public void setCourt(QLSModel court) {
        this.court = court;
    }

    public LocalDateTime getNgayDanhGia() {
        return ngayDanhGia;
    }

    public void setNgayDanhGia(LocalDateTime ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }

    @ManyToOne
    @JoinColumn(name = "court_id")
    private QLSModel court;

    private LocalDateTime ngayDanhGia = LocalDateTime.now();
}

