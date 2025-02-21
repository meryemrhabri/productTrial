package com.alten.kata;

import com.alten.kata.entity.CartProduct;
import com.alten.kata.entity.User;
import com.alten.kata.exception.ProductException;
import com.alten.kata.exception.UserException;
import com.alten.kata.repository.ProductRepository;
import com.alten.kata.repository.UserRepository;
import com.alten.kata.entity.Cart;
import com.alten.kata.entity.Product;
import com.alten.kata.repository.CartProductsRepository;
import com.alten.kata.repository.CartRepository;
import com.alten.kata.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartProductsRepository cartProductsRepository;


    @InjectMocks
    private CartService cartService;

    private User user;
    private Product product;
    private Cart cart;
    private CartProduct cartProduct;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("produit1");
        user = new User();
        user.setEmail("test@test.com");
        cart = new Cart(user);
        cart.setId(1L);
        cartProduct= new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(3);
        cart.setItems(List.of(cartProduct));
    }

    @Test
    void createCartwithExisintigUser() throws UserException {
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        when(cartRepository.findByUser(user)).thenReturn(null);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        Cart cartResult = cartService.createCart(user.getEmail());
        assertNotNull(cartResult);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
    @Test
    void createCartWithUserNotFound() {

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);
        Exception exception = assertThrows(UserException.class, () -> {
            cartService.createCart(user.getEmail());
        });
        assertEquals("Utilisateur non trouve", exception.getMessage());
    }
    @Test
    void addProductToCart() throws UserException, ProductException {
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        when(cartRepository.findByUser(user)).thenReturn(cart);
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(product)).thenReturn(product);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartProductsRepository.save(cartProduct)).thenReturn(cartProduct);
        Cart cartResult= cartService.addToCart(user.getEmail(), product.getId(),4);
        assertNotNull(cartResult);
        assertEquals(cartResult.getItems().get(0).getProduct().getId(),product.getId());
    }
}
