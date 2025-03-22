package uth.edu.jpa.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Field;
import uth.edu.jpa.repositories.FieldRepository;
import uth.edu.jpa.services.FieldService;
import java.util.List;
import java.util.Optional;

@Service
public class FieldService {
    private final FieldRepository FieldRepository;
    private FieldRepository fieldRepository;
    private FieldRepository fieldRepository1;

    @Autowired



    public Field saveField(Field Field) {
        return fieldRepository.save(Field);
    }
    public FieldService(FieldRepository FieldRepository) {
        this.FieldRepository = FieldRepository;
    }
    // Lấy danh sách tất cả sân bóng
    public List<Field> getAllFields() {
        return FieldRepository.findAll();
    }
    // Tìm sân bóng theo ID
    public Optional<Field> getFieldById(Long id) {
        return FieldRepository.findById(id);
    }
    // Thêm sân bóng mới
    public Field addFootballField(Field Field) {
        return FieldRepository.save(Field);
    }
    // Cập nhật thông tin sân bóng
    public Field updateField(Long id, Field fieldDetails) {
        return FieldRepository.findById(id).map(field -> {
            field.setName(fieldDetails.getName());
            field.setLocation(fieldDetails.getLocation());
            field.setPricePerHour(fieldDetails.getPricePerHour());
            field.setAvailable(fieldDetails.getAvailable());
            return FieldRepository.save(field);
        }).orElseThrow(() -> new RuntimeException("field not found"));

    }

    // Xóa sân bóng
    public boolean deleteField(Long id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
            return true; // Trả về true nếu xóa thành công
        }
        return false; // Trả về false nếu không tìm thấy ID
    }

}
