package com.example.altenKata;

import com.example.altenKata.model.Product;
import com.example.altenKata.repository.ProductRepository;
import com.example.altenKata.service.ProduitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProduitServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProduitService produitService;

    private Product product;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "P1", "produit1", "descriptio produit", "image.jpg", "accessoires", 10.0, 3L, "Ref1", 1L, "INSTOCK", 5L, "2025-02-20", "2025-02-21");
    }
    @Test
    void shouldCreateProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = produitService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("produit1", createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldGetAllProduct() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> listProduct = produitService.getAllProduct();

        assertNotNull(listProduct);
        assertEquals(1, listProduct.size());
        verify(productRepository, times(1)).findAll();
    }
    @Test
    void shouldGetOneProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> product = produitService.getProductWithId(1L);

        assertTrue(product.isPresent());
        assertEquals("descriptio produit", product.get().getDescription());
    }
    @Test
    void shouldUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Optional<Product> updatedProduct = produitService.updateProduct(1L, product);

        assertTrue(updatedProduct.isPresent());
        assertEquals("accessoires", updatedProduct.get().getCategory());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
