package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController 
@RequestMapping("/api/carts")
@RequiredArgsConstructor 
public class CartController {
    private final CartService cartService;

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    @PostMapping("/{cartId}/items")
    public Cart addItemToCart(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addItemToCart(cartId, productId, quantity);
}

    @DeleteMapping ("/{cartId}/items/{productId}")
    public Cart removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.removeItemFromCart(cartId, productId);
    }

    @PostMapping("/{cartId}/checkout")
    public Cart checkoutCart(@PathVariable Long cartId) {
        return cartService.checkoutCart(cartId);
    }
    


}
