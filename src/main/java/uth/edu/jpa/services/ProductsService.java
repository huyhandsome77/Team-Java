package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Products;
import uth.edu.jpa.repositories.ProductsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository repository;
    //lay tat ca sp
    public List<Products> getAllProducts() {
        return repository.findAll();
    }
    // Lay sp theo id
    public Optional<Products> getProductById(Long id) {
        return repository.findById(id);
    }
    //them or cap nhat sp
    public Products saveProduct(Products product) {
        return repository.save(product);
    }

    //de le te san pham theo id
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
    // cap nhat so luong ton kho
    public Products updateInventory(Long id, int quantity) {
        Products product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setInventory(product.getInventory() + quantity);
        return repository.save(product);
    }
}
