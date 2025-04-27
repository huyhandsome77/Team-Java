package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Support;
import uth.edu.jpa.repositories.SupportRepository;

@Service
public class SupportService {

    @Autowired
    private SupportRepository supportRepository;

    // Lấy thông tin hỗ trợ (Support)
    public Support getSupportData() {
        return supportRepository.findFirstByOrderByCreatedAtDesc()
                .orElse(new Support()); // Trả về một đối tượng Support mới nếu không tìm thấy dữ liệu
    }

    // Có thể thêm các phương thức khác cho các hoạt động với yêu cầu hỗ trợ
}
