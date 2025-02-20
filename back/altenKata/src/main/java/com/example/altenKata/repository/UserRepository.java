package com.example.altenKata.repository;

import com.example.altenKata.model.Product;
import com.example.altenKata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);

}
