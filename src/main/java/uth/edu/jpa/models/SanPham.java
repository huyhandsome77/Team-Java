package uth.edu.jpa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "SanPham")
public class SanPham {

    public enum LoaiSanPham {
        DO_AN, NUOC_UONG, DUNG_CU
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long maSanPham;

    private String tenSanPham;
    private double gia;

    @Enumerated(EnumType.STRING)
    private LoaiSanPham loaiSanPham;

    private int tonKho;
    private String img;

    public SanPham() {}

    public SanPham(String tenSanPham, double gia, LoaiSanPham loaiSanPham, int tonKho, String img) {
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.loaiSanPham = loaiSanPham;
        this.tonKho = tonKho;
        this.img = img;
    }

    // Getters & Setters

    public long getMaSanPham() { return maSanPham; }

    public void setMaSanPham(long maSanPham) { this.maSanPham = maSanPham; }


    public String getTenSanPham() { return tenSanPham; }

    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public double getGia() { return gia; }

    public void setGia(double gia) { this.gia = gia; }

    public LoaiSanPham getLoaiSanPham() { return loaiSanPham; }

    public void setLoaiSanPham(LoaiSanPham loaiSanPham) { this.loaiSanPham = loaiSanPham; }

    public int getTonKho() { return tonKho; }

    public void setTonKho(int tonKho) { this.tonKho = tonKho; }

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

    @Override
    public String toString() {
        return "SanPham{" +
                "maSanPham=" + maSanPham +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", gia=" + gia +
                ", loaiSanPham=" + loaiSanPham +
                ", tonKho=" + tonKho +
                ", img='" + img + '\'' +
                '}';
    }
}
