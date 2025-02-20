package com.example.altenKata.repository;

import com.example.altenKata.model.CartProduct;
import com.example.altenKata.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByProductId(Long productId);
}
