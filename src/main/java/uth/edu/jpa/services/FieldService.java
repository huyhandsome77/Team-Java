package uth.edu.jpa.services;

import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Field;
import uth.edu.jpa.repositories.FieldRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    public Optional<Field> getFieldById(Long id) {
        return fieldRepository.findById(id);
    }

    public Field saveField(Field field) {
        return fieldRepository.save(field);
    }

    public Optional<Field> updateField(Long id, Field fieldDetails) {
        return fieldRepository.findById(id).map(field -> {
            field.setName(fieldDetails.getName());
            field.setLocation(fieldDetails.getLocation());
            field.setPricePerHour(fieldDetails.getPricePerHour());
            field.setAvailable(fieldDetails.isAvailable());
            return fieldRepository.save(field);
        });
    }

    public boolean deleteField(Long id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
