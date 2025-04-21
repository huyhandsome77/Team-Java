package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.jpa.models.SanPham;
import uth.edu.jpa.repositories.SanPhamRepository;

import java.io.File;
import java.util.List;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("/upload")
    public String Uploadform() {
        return "upload"; // HTML form để upload ảnh
    }

    @PostMapping("/sanpham/save")
    public String saveSanPham(
            @RequestParam("tenSanPham") String tenSanPham,
            @RequestParam("gia") double gia,
            @RequestParam("loaiSanPham") SanPham.LoaiSanPham loaiSanPham,
            @RequestParam("tonKho") int tonKho,
            @RequestParam("image") MultipartFile imageFile,
            Model model
    ) {
        try {
            String projectRoot = System.getProperty("user.dir");
            String uploadDir = projectRoot + "/src/main/resources/static/assets/img/elements/SanPham/";

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            String fileName = imageFile.getOriginalFilename();
            File saveFile = new File(uploadPath, fileName);
            imageFile.transferTo(saveFile);

            SanPham sp = new SanPham();
            sp.setTenSanPham(tenSanPham);
            sp.setGia(gia);
            sp.setLoaiSanPham(loaiSanPham); // enum type
            sp.setTonKho(tonKho);
            sp.setImg(fileName);

            sanPhamRepository.save(sp);
            return "redirect:/sanpham";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi thêm sản phẩm.");
            return "error";
        }
    }
}