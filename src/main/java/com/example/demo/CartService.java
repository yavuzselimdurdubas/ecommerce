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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
    CartItem existingItem = null;
    for (CartItem item : cart.getItems()) {
        if (item.getProduct().getId().equals(productId)) {
            existingItem = item;
            break;
        }
    }
    
    if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cartItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
        cartRepository.save(cart);
        return cart;
    }


    public Cart removeItemFromCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found for product id: " + productId));
        cart.getItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
        return cart;
    }
    
}
