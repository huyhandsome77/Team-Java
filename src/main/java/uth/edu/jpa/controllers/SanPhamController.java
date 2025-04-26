package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.models.SanPham;
import uth.edu.jpa.repositories.SanPhamRepository;

import java.io.File;
import java.util.Optional;

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
            RedirectAttributes redirectAttributes
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
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");
            return "redirect:/admin/quanlibanhang";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm sản phẩm.");
            return "redirect:/admin/quanlibanhang";
        }
    }

    @GetMapping("/sanpham/sua/{id}")
    public String editSanPhamForm(@PathVariable("id") Long id, Model model) {
        Optional<SanPham> sanPhamOpt = sanPhamRepository.findById(id);
        if (sanPhamOpt.isPresent()) {
            model.addAttribute("sanPham", sanPhamOpt.get());
            return "editSanPham"; // HTML form để sửa sản phẩm
        } else {
            return "redirect:/admin/quanlibanhang";
        }
    }

    @PostMapping("/sanpham/update")
    public String updateSanPham(
            @RequestParam("id") Long id,  // Sửa id thành Long
            @RequestParam("tenSanPham") String tenSanPham,
            @RequestParam("gia") double gia,
            @RequestParam("loaiSanPham") SanPham.LoaiSanPham loaiSanPham,
            @RequestParam("tonKho") int tonKho,
            @RequestParam("image") MultipartFile imageFile,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Optional<SanPham> sanPhamOpt = sanPhamRepository.findById(id);
            if (sanPhamOpt.isPresent()) {
                SanPham sp = sanPhamOpt.get();

                // Cập nhật thông tin
                sp.setTenSanPham(tenSanPham);
                sp.setGia(gia);
                sp.setLoaiSanPham(loaiSanPham);
                sp.setTonKho(tonKho);

                // Xử lý hình ảnh nếu có thay đổi
                if (!imageFile.isEmpty()) {
                    String projectRoot = System.getProperty("user.dir");
                    String uploadDir = projectRoot + "/src/main/resources/static/assets/img/elements/SanPham/";

                    File uploadPath = new File(uploadDir);
                    if (!uploadPath.exists()) {
                        uploadPath.mkdirs();
                    }

                    String fileName = imageFile.getOriginalFilename();
                    File saveFile = new File(uploadPath, fileName);
                    imageFile.transferTo(saveFile);

                    sp.setImg(fileName); // Cập nhật ảnh mới
                }

                sanPhamRepository.save(sp);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại.");
            }
            return "redirect:/admin/quanlibanhang";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật sản phẩm.");
            return "redirect:/admin/quanlibanhang";
        }
    }

    @GetMapping("/sanpham/xoa/{id}")
    public String deleteSanPham(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            sanPhamRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa sản phẩm.");
        }
        return "redirect:/admin/quanlibanhang";
    }
}
