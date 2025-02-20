package com.example.altenKata.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.altenKata.model.Cart;
import com.example.altenKata.model.CartProduct;
import com.example.altenKata.model.Product;
import com.example.altenKata.model.User;
import com.example.altenKata.repository.CartProductsRepository;
import com.example.altenKata.repository.CartRepository;
import com.example.altenKata.repository.ProductRepository;
import com.example.altenKata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthentificationService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Value("${jwt.secret}")
    private String secret;

    public AuthentificationService( UserRepository userRepository) {

        this.userRepository=userRepository;
    }


    public ResponseEntity<String> getToken(User user) {
        User userConnected = userRepository.findUserByEmail(user.getEmail());
        if(userConnected == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        if (!passwordEncoder.matches(user.getPassword(), userConnected.getPassword())) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        String token = JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .sign(Algorithm.HMAC256(secret));

        return ResponseEntity.ok(token);
    }
}
