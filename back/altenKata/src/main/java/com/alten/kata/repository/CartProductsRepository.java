package com.alten.kata.repository;

import com.alten.kata.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByProductId(Long productId);
}
