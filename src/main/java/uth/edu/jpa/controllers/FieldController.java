
package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Field;
import uth.edu.jpa.services.FieldService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @GetMapping
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> getFieldById(@PathVariable Long id) {
        Optional<Field> field = fieldService.getFieldById(id);
        return field.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Field> createField(@RequestBody Field field) {
        Field newField = fieldService.saveField(field);
        return ResponseEntity.ok(newField);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Field>> updateField(@PathVariable Long id, @RequestBody Field fieldDetails) {
        Optional<Field> fieldOptional = fieldService.getFieldById(id);
        if (fieldOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Field> updatedField = fieldService.updateField(id, fieldDetails);
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        boolean deleted = fieldService.deleteField(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
