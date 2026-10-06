package com.example.demo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        if(product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        if (existingProduct != null) {
            if(updatedProduct.getPrice() < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
