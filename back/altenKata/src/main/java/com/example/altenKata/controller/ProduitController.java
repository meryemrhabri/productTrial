package com.example.altenKata.controller;

import com.example.altenKata.model.Product;
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
@RequestMapping("/products")
public class ProduitController {
    private final Logger logger = LoggerFactory.getLogger(ProduitController.class);
    @Resource
    private ProduitService produitService;

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
    public ResponseEntity<?> createProduct(@RequestBody Product request) {

        logger.info("create new Product...");
        try {
            if (!getAuthenticatedUserEmail().equals("admin@admin.com")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Only admin can add products.");
            }
          Product product = produitService.createProduct(request);

            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during creation Product", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Récupérer la liste des produits
     * @return List Products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {

        // Log start
        logger.info("get all product...");
        try {
            List<Product> products = produitService.getAllProduct();
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
    public ResponseEntity getProductByid(@PathVariable Long id) {

        logger.info("get product with id ");
        try {
            Optional<Product> product = produitService.getProductWithId(id);
            return product.isPresent() ? new ResponseEntity<>(product, HttpStatus.OK):new ResponseEntity<>("produit non trouvé ", HttpStatus.NOT_FOUND);

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
    public ResponseEntity updateProduct(@PathVariable Long id,@RequestBody Product product) {

        logger.info("update product with id ");
        try {
            if (!getAuthenticatedUserEmail().equals("admin@admin.com")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Only admin can add products.");
            }
            Optional<Product> productUpdated = produitService.updateProduct(id,product);
            return productUpdated.isPresent() ? new ResponseEntity<>(productUpdated, HttpStatus.OK):new ResponseEntity<>("produit non trouvé ", HttpStatus.NOT_FOUND);

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
    public ResponseEntity deleteProduct(@PathVariable Long id) {

        logger.info("update product with id ");
        try {
            if (!getAuthenticatedUserEmail().equals("admin@admin.com")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Only admin can add products.");
            }
            boolean deletedProduct = produitService.deleteProductWithId(id);
            return deletedProduct? new ResponseEntity<>("Produit supprimé avec succés", HttpStatus.OK):new ResponseEntity<>("Produit non trouvé ", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.error("Error during update Product", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
