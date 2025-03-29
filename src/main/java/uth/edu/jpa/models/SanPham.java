package uth.edu.jpa.models;
import jakarta.persistence.*;
@Entity
@Table (name="SanPham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int MaSanpham; //Id
    private String TenSanPham;
    private double Gia;
    private int LoaiSanPham;
    private int TonKho;
    //getter và setter

    public int getMaSanpham() {
        return MaSanpham;
    }

    public void setMaSanpham(int maSanpham) {
        MaSanpham = maSanpham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double gia) {
        Gia = gia;
    }

    public int getLoaiSanPham() {
        return LoaiSanPham;
    }

    public void setLoaiSanPham(int loaiSanPham) {
        LoaiSanPham = loaiSanPham;
    }

    //constuctor
    public SanPham(int tonKho) {
        TonKho = tonKho;
    }

}