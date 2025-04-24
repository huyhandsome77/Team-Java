package uth.edu.jpa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class DuBaoThoiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private LocalDate ngay;
    private float nhietDo;
    private float doAm;
    private float tocDoGio;
    private String duBaoAi;
    private float precipitation;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public LocalDate getNgay() { return ngay; }
    public void setNgay(LocalDate ngay) { this.ngay = ngay; }
    public float getNhietDo() { return nhietDo; }
    public void setNhietDo(float nhietDo) { this.nhietDo = nhietDo; }
    public float getDoAm() { return doAm; }
    public void setDoAm(float doAm) { this.doAm = doAm; }
    public float getTocDoGio() { return tocDoGio; }
    public void setTocDoGio(float tocDoGio) { this.tocDoGio = tocDoGio; }
    public String getDuBaoAi() { return duBaoAi; }
    public void setDuBaoAi(String duBaoAi) { this.duBaoAi = duBaoAi; }
    public float getPrecipitation() { return precipitation; }
    public void setPrecipitation(float precipitation) { this.precipitation = precipitation; }
}