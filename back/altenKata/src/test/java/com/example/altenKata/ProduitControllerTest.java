package com.example.altenKata;

import com.example.altenKata.controller.ProduitController;
import com.example.altenKata.model.Product;
import com.example.altenKata.repository.ProductRepository;
import com.example.altenKata.service.ProduitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProduitControllerTest {
    @InjectMocks
    private ProduitController produitController;

    @Mock
    private ProduitService produitService;

    private Product product;
    private Product product2;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "P1", "produit1", "descriptio produit", "image.jpg", "accessoires", 10.0, 3L, "Ref1", 1L, "INSTOCK", 5L, "2025-02-20", "2025-02-21");
        product2 = new Product(2L, "P2", "produit2", "descriptio produit2", "image.jpg", "accessoires", 10.0, 3L, "Ref1", 1L, "INSTOCK", 5L, "2025-02-20", "2025-02-21");

    }
    private void mockUser(String email){
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(email);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void shouldCreateProduct() {
        mockUser("admin@admin.com");
        when(produitService.createProduct(product)).thenReturn(product);

        ResponseEntity createdProduct = produitController.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals(HttpStatus.OK,createdProduct.getStatusCode());
        verify(produitService, times(1)).createProduct(product);
    }
    @Test
    void shouldNotAccesToCreateProduct() {
        mockUser("mm@mm.com");
        when(produitService.createProduct(product)).thenReturn(product);

        ResponseEntity createdProduct = produitController.createProduct(product);

        assertEquals(HttpStatus.FORBIDDEN,createdProduct.getStatusCode());
    }

    @Test
    void shouldGetAllProduct() {
        mockUser("mm@mm.com");
        when(produitService.getAllProduct()).thenReturn(List.of(product));
        ResponseEntity productsList=produitController.getAllProduct();
        assertNotNull(productsList.getBody());
    }
    @Test
    void shouldGetOneProduct() {
        mockUser("mm@mm.com");
        when(produitService.getProductWithId(1L)).thenReturn(Optional.ofNullable(product));
        ResponseEntity productsList=produitController.getProductByid(1L);
        assertEquals(HttpStatus.OK,productsList.getStatusCode());
    }
    @Test
    void shouldUpdateProduct() {
        mockUser("admin@admin.com");
        when(produitService.updateProduct(1L,product2)).thenReturn(Optional.ofNullable(product2));

        ResponseEntity updatedProduct = produitController.updateProduct(1L,product2);

        assertNotNull(updatedProduct);
        assertEquals(HttpStatus.OK,updatedProduct.getStatusCode());
        verify(produitService, times(1)).updateProduct(1L,product2);
    }
    @Test
    void shouldUpdateProductNotFound() {
        mockUser("admin@admin.com");
        when(produitService.updateProduct(3L,product2)).thenReturn(Optional.empty());

        ResponseEntity updatedProduct = produitController.updateProduct(3L,product2);

        assertNotNull(updatedProduct);
        assertEquals(HttpStatus.NOT_FOUND,updatedProduct.getStatusCode());
        verify(produitService, times(1)).updateProduct(3L,product2);
    }
    @Test
    void shouldNotAccesToUpdateProduct() {
        mockUser("mm@mm.com");
        when(produitService.updateProduct(1L,product2)).thenReturn(Optional.ofNullable(product2));

        ResponseEntity updatedProduct = produitController.updateProduct(1L,product2);

        assertEquals(HttpStatus.FORBIDDEN,updatedProduct.getStatusCode());
    }
    @Test
    void shouldDeleteProduct() {
        mockUser("admin@admin.com");
        when(produitService.deleteProductWithId(1L)).thenReturn(Boolean.TRUE);

        ResponseEntity deleteProduct = produitController.deleteProduct(1L);

        assertNotNull(deleteProduct);
        assertEquals(HttpStatus.OK,deleteProduct.getStatusCode());
        verify(produitService, times(1)).deleteProductWithId(1L);
    }
    @Test
    void shouldUpdateDeleteNotFound() {
        mockUser("admin@admin.com");
        when(produitService.deleteProductWithId(3L)).thenReturn(Boolean.FALSE);

        ResponseEntity deletedProduct = produitController.deleteProduct(3L);

        assertNotNull(deletedProduct);
        assertEquals(HttpStatus.NOT_FOUND,deletedProduct.getStatusCode());
        verify(produitService, times(1)).deleteProductWithId(3L);
    }
    @Test
    void shouldNotAccesToDeleteProduct() {
        mockUser("mm@mm.com");
        when(produitService.updateProduct(1L,product2)).thenReturn(Optional.ofNullable(product2));

        ResponseEntity deletedProduct = produitController.deleteProduct(3L);

        assertEquals(HttpStatus.FORBIDDEN,deletedProduct.getStatusCode());
    }
}
