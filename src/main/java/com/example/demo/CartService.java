package com.example.demo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service   
@RequiredArgsConstructor 
public class CartService {
private final CartRepository cartRepository;
private final ProductRepository productRepository;
private final CartItemRepository cartItemRepository;

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid cart ID: " + id);
        }
        return cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + id));
    }

    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        cart.getItems().add(cartItem);
        return cart;
    }
}
