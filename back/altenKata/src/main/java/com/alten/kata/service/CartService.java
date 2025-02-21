package com.alten.kata.service;

import com.alten.kata.entity.Cart;
import com.alten.kata.entity.CartProduct;
import com.alten.kata.entity.Product;
import com.alten.kata.entity.User;
import com.alten.kata.exception.CartException;
import com.alten.kata.exception.ProductException;
import com.alten.kata.exception.UserException;
import com.alten.kata.repository.CartRepository;
import com.alten.kata.repository.ProductRepository;
import com.alten.kata.repository.UserRepository;
import com.alten.kata.repository.CartProductsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    private final CartProductsRepository cartProductsRepository;
    private final String messageUsernotFound = "Utilisateur non trouve";

    public CartService(ProductRepository productRepository,CartRepository cartRepository,UserRepository userRepository,CartProductsRepository cartProductsRepository) {
        this.productRepository = productRepository;
        this.cartRepository= cartRepository;
        this.userRepository=userRepository;
        this.cartProductsRepository=cartProductsRepository;
    }


    public Cart createCart(String email) throws UserException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UserException(messageUsernotFound);
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart== null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        return cart;
    }
    public Cart getCart(User user) {
        return cartRepository.findByUser(user);
    }
    public Cart addToCart(String email, Long productId, int quantity) throws UserException, ProductException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UserException(messageUsernotFound);
        }
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()) {
            throw new ProductException("Produit not trouve");
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        Optional<CartProduct> cartProduct=cartProductsRepository.findByProductId(product.get().getId());

        if(cartProduct.isPresent()) {
            cartProduct.get().setQuantity(quantity);
        }else {
            cartProduct= Optional.of(new CartProduct(cart, product.get(), quantity));

        }
        cartProductsRepository.save(cartProduct.get());
        return cart;
    }
    public Cart updateQantity(String email, Long productId, int quantity) throws UserException, ProductException, CartException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UserException(messageUsernotFound);
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart == null) {
            throw new CartException("cart non trouvé");
        }
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()) {
            throw new ProductException("Produit non trouvé");
        }
        CartProduct cartProduct=new CartProduct(cart,product.get(),quantity);
        cartProductsRepository.save(cartProduct);
        return cart;
    }
    public Cart removeFromCart(String email, Long productId) throws UserException, CartException, ProductException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
           throw new UserException(messageUsernotFound);
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart == null) {
            throw new CartException("cart not found");
        }
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()) {
            new ProductException("Produit not found");
        }
        Optional<CartProduct> cartProduct = cartProductsRepository.findByProductId(productId);
        if(cartProduct.isPresent()) {
            cartProductsRepository.delete(cartProduct.get());
        }else {
            new ProductException("Produit inexistant dans panier");
        }
        return getCart(user);
    }
}
