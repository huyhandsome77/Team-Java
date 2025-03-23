package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Products;
import uth.edu.jpa.services.ProductsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService service;

    //API get sp
    @GetMapping
    public List<Products> getAllProducts() {
        return service.getAllProducts();
    }

    //APIget sp theo id
    @GetMapping("/{id}")
    public Optional<Products> getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    //API them moi or cap nhat sp
    @PostMapping
    public Products createProduct(@RequestBody Products product) {
        return service.saveProduct(product);
    }

    //API cap nhat sp
    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable Long id, @RequestBody Products product) {
        product.setProductID(Math.toIntExact(id)); // Cập nhật ID
        return service.saveProduct(product);
    }

    //API de le te sp
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }

    //API cap nhat so luong ton kho
    @PutMapping("/{id}/inventory/{quantity}")
    public Products updateInventory(@PathVariable Long id, @PathVariable int quantity) {
        return service.updateInventory(id, quantity);
    }
}
