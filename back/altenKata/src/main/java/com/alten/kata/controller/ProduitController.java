package com.alten.kata.controller;

import com.alten.kata.entity.Product;
import com.alten.kata.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProduitController {
    private final Logger logger = LoggerFactory.getLogger(ProduitController.class);
    private ProductService productService;
    private String mailAdmin="admin@admin.com";
    private String message="Acces Non autorisé: seuls les admins peuvent gérer produit.";
    public ProduitController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Récupérer Mail de l'utilisateur connecté
     * @return
     */
    private String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    /**
     * créer un produit
     * @param request
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product request) {

        logger.info("create new Product...");
        try {
            if (!getAuthenticatedUserEmail().equals(mailAdmin)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
            }
            productService.createProduct(request);

            return new ResponseEntity<>("produit ajouté avec succés", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during creation Product {}", e.getMessage());
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Récupérer la liste des produits
     * @return List Products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {

        logger.info("get all product...");
        try {
            List<Product> products = productService.getAllProduct();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during get Products", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * récupérer Produit en donnant un id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductByid(@PathVariable Long id) {

        logger.info("get product with id {}", id);
        try {
            Optional<Product> product = productService.getProductWithId(id);
            return product.isPresent() ? new ResponseEntity<>(product.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error during get Products", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Modifier un produit défini
     * @param id
     * @param product
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,@RequestBody Product product) {

        logger.info("update product with id {}",id);
        try {
            if (!getAuthenticatedUserEmail().equals(mailAdmin)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
            }
            Optional<Product> productUpdated = productService.updateProduct(id,product);
            return productUpdated.isPresent() ? new ResponseEntity<>("produit modifié avec succés", HttpStatus.OK):new ResponseEntity<>("produit non trouvé ", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.error("Error during update Product", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprimer un produit défini
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        logger.info("delete product with id {}",id);
        try {
            if (!getAuthenticatedUserEmail().equals(mailAdmin)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
            }
            boolean deletedProduct = productService.deleteProductWithId(id);
            return deletedProduct ? new ResponseEntity<>("Produit supprimé avec succés", HttpStatus.OK):new ResponseEntity<>("Produit non trouvé ", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.error("Error during delete Product", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
