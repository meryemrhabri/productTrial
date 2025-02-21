package com.alten.kata.repository;

import com.alten.kata.entity.User;
import com.alten.kata.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
public Cart findByUser(User user);

}
