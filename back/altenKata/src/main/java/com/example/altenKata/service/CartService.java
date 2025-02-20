package com.example.altenKata.service;

import com.example.altenKata.model.Cart;
import com.example.altenKata.model.CartProduct;
import com.example.altenKata.model.Product;
import com.example.altenKata.model.User;
import com.example.altenKata.repository.CartProductsRepository;
import com.example.altenKata.repository.CartRepository;
import com.example.altenKata.repository.ProductRepository;
import com.example.altenKata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CartProductsRepository cartProductsRepository;

    public CartService(ProductRepository productRepository,CartRepository cartRepository,UserRepository userRepository,CartProductsRepository cartProductsRepository) {
        this.productRepository = productRepository;
        this.cartRepository= cartRepository;
        this.userRepository=userRepository;
        this.cartProductsRepository=cartProductsRepository;
    }


    public Cart createCart(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            new RuntimeException("User not found");
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart== null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        return cart;
    }
    public Cart getCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        return cart;
    }
    public Cart addToCart(String email, Long productId, int quantity) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new RuntimeException("User not found");
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            throw new RuntimeException("Produit not found");
        }
        CartProduct cartProduct=new CartProduct(cart,product.get(),quantity);
        cartProductsRepository.save(cartProduct);
        return cart;
    }
    public Cart updateQuantiy(String email, Long productId, int quantity) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new RuntimeException("User non trouvé");
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart == null) {
            throw new RuntimeException("cart non trouvé");
        }
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            throw new RuntimeException("Produit non trouvé");
        }
        CartProduct cartProduct=new CartProduct(cart,product.get(),quantity);
        cartProductsRepository.save(cartProduct);
        return cart;
    }
    public Cart removeFromCart(String email, Long productId) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
           throw new RuntimeException("User not found");
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart == null) {
            throw new RuntimeException("cart not found");
        }
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            new RuntimeException("Produit not found");
        }
        Optional<CartProduct> cartProduct = cartProductsRepository.findByProductId(productId);
        if(cartProduct.isPresent()) {
            new RuntimeException("Produit not found");
        }
        cartProductsRepository.delete(cartProduct.get());
        return getCart(user);
    }
    public List<CartProduct> getCartItems(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            new RuntimeException("User not found");
        }
        Cart cart = getCart(user);
        return cart.getItems();
    }
}
