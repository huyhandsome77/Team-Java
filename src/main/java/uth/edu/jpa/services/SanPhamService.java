package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.SanPham;
import uth.edu.jpa.repositories.SanPhamRepository;

import java.util.List;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;  // Dùng repository để tương tác với database

    public List<SanPham> findAll() {
        return sanPhamRepository.findAll();  // Trả về danh sách sản phẩm từ database
    }
}