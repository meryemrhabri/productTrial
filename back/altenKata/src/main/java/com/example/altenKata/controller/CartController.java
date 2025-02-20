package com.example.altenKata.controller;

import com.example.altenKata.model.Cart;
import com.example.altenKata.model.CartRequestDTO;
import com.example.altenKata.model.Product;
import com.example.altenKata.model.User;
import com.example.altenKata.service.CartService;
import com.example.altenKata.service.ProduitService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final Logger logger = LoggerFactory.getLogger(CartController.class);
    @Resource
    private CartService cartService;
    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * permettre de créer un panier de l'utilisateur connecté
     * @return ResponseEntity avec un message
     */
    @PostMapping()
    public ResponseEntity<?> crerPanier() {
        try {
            cartService.createCart(getAuthenticatedUserEmail());
            return new ResponseEntity<>("panier ajouté avec succés", HttpStatus.OK);

        }catch (Exception e) {
            logger.error("Error during creation panier", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Permettre d'ajouter un produit au panier avec une quantité précise  de l'utilisateur connecté
     * @param productId
     * @param requestDTO
     * @return
     */
    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Long productId, @RequestBody CartRequestDTO requestDTO) {
        try {
            Cart cart=cartService.addToCart(getAuthenticatedUserEmail(), productId, requestDTO.getQuantity());
            return new ResponseEntity<>("produit ajouté avec succés", HttpStatus.OK);

        }catch (Exception e) {
            logger.error("Error during creation User", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Permettre de modifier la quantité d'un produit dans un panier de l'utilisateur connecté
     * @param productId
     * @param requestDTO
     * @return
     */
    @PutMapping("/edit/{productId}")
    public ResponseEntity<?> editSuantiyy(@PathVariable Long productId, @RequestBody CartRequestDTO requestDTO) {
        try {
            Cart cart=cartService.addToCart(getAuthenticatedUserEmail(), productId, requestDTO.getQuantity());
            return new ResponseEntity<>("produit ajouté avec succés", HttpStatus.OK);

        }catch (Exception e) {
            logger.error("Error during creation User", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Permet de supprimer un produit du panier de l'utilisateur connecté
     * @param productId
     * @return
     */
    @PostMapping("/delete/{productId}")
    public ResponseEntity<?> deleteFromCart(@PathVariable Long productId) {
        try {
            Cart cart=cartService.removeFromCart(getAuthenticatedUserEmail(), productId);
            return new ResponseEntity<>("produit supprimé avec succés", HttpStatus.OK);

        }catch (Exception e) {
            logger.error("Error during supression cart", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
