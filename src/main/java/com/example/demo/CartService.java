package com.example.demo;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.ArrayList;


@Service   
@RequiredArgsConstructor 
public class CartService {
private final CartRepository cartRepository;
private final ProductRepository productRepository;
private final CartItemRepository cartItemRepository;

    public CartDto createCart(Cart cart) {
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    public Cart getCartEntity(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid cart ID: " + id);
        }
        return cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + id));
    }
    
    public CartDto getCartById(Long id){
        return convertToDto(getCartEntity(id));
    }

    public CartDto addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = getCartEntity(cartId);
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
            cartItemRepository.save(existingItem);
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
        return convertToDto(cart);
    }


    public CartDto removeItemFromCart(Long cartId, Long productId) {
        Cart cart = getCartEntity(cartId);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found for product id: " + productId));
        cart.getItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
        return convertToDto(cart);
    }

    public CartDto checkoutCart(Long cartId) {
        Cart cart = getCartEntity(cartId);
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
        return convertToDto(cart);
    }
    
    private CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalPrice(cart.getTotalPrice());   
        List<CartItemDto> itemDtos = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setProductPrice(item.getProduct().getPrice());
            itemDto.setQuantity(item.getQuantity());
            itemDtos.add(itemDto);
        }
        cartDto.setItems(itemDtos);
        return cartDto;
    }
}
