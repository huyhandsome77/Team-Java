package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Field;
import uth.edu.jpa.services.FieldService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fields") // Định nghĩa đường dẫn API
public class FieldController {

    @Autowired
    private FieldService fieldService;

    // Lấy danh sách tất cả các Field
    @GetMapping
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }

    // Lấy thông tin Field theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Field> getFieldById(@PathVariable Long id) {
        Optional<Field> field = fieldService.getFieldById(id);
        return field.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới một Field
    @PostMapping
    public ResponseEntity<Field> createField(@RequestBody Field field) {
        Field newField = fieldService.saveField(field);
        return ResponseEntity.ok(newField);
    }

    // Cập nhật thông tin một Field
    @PutMapping("/{id}")
    public ResponseEntity<Field> updateField(@PathVariable Long id, @RequestBody Field fieldDetails) {
        Optional<Field> updatedField = fieldService.updateField(id, fieldDetails);
        return updatedField.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Xóa một Field theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        boolean deleted = fieldService.deleteField(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
