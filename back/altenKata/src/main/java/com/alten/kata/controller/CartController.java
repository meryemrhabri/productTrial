package com.alten.kata.controller;

import com.alten.kata.exception.CartException;
import com.alten.kata.exception.ProductException;
import com.alten.kata.exception.UserException;
import com.alten.kata.model.CartRequestDTO;
import com.alten.kata.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * permettre de créer un panier de l'utilisateur connecté
     * @return ResponseEntity avec un message
     */
    @PostMapping()
    public ResponseEntity<String> crerPanier() {
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
    @PostMapping("product/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long productId, @RequestBody CartRequestDTO requestDTO) {

        try {
            cartService.addToCart(getAuthenticatedUserEmail(), productId, requestDTO.getQuantity());
            return new ResponseEntity<>("produit ajouté avec succés", HttpStatus.OK);

        } catch (UserException | ProductException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    /**
     * Permettre de modifier la quantité d'un produit dans un panier de l'utilisateur connecté
     * @param productId
     * @param requestDTO
     * @return
     */
    @PutMapping("/product/{productId}")
    public ResponseEntity<String> editQantity(@PathVariable Long productId, @RequestBody CartRequestDTO requestDTO) {

        try {
            cartService.updateQantity(getAuthenticatedUserEmail(), productId, requestDTO.getQuantity());
            return new ResponseEntity<>("produit modifié avec succés", HttpStatus.OK);
         } catch (UserException | ProductException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
        catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    }

    /**
     * Permet de supprimer un produit du panier de l'utilisateur connecté
     * @param productId
     * @return
     */
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteFromCart(@PathVariable Long productId) {
        try {
            cartService.removeFromCart(getAuthenticatedUserEmail(), productId);
            return new ResponseEntity<>("produit supprimé avec succés", HttpStatus.OK);
        } catch (UserException | ProductException | CartException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
