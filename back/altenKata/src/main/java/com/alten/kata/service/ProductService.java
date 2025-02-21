package com.alten.kata.service;

import com.alten.kata.repository.ProductRepository;
import com.alten.kata.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product request) {
        return productRepository.save(request);
    }

    public List<Product> getAllProduct() {
        return  productRepository.findAll();
    }
    public Optional<Product> getProductWithId(Long id) {
        return  productRepository.findById(id);
    }
    public Optional<Product> updateProduct(Long id,Product newValues) {
            return  productRepository.findById(id).map(product -> {
                product.setName(newValues.getName());
                product.setDescription(newValues.getDescription());
                product.setPrice(newValues.getPrice());
                product.setCategory(newValues.getCategory());
                product.setImage(newValues.getImage());
                return productRepository.save(product);
            });
    }

    public boolean deleteProductWithId(Long id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
             return true;
        }
        return false;
    }
}
