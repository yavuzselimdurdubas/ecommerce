package com.example.demo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.example.demo.dto.ProductDto;
import java.util.List;
import java.util.ArrayList;

@Service 
@RequiredArgsConstructor 
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto createProduct(Product product) {
        if(product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return convertToDto(productRepository.save(product));
    }

    public ProductDto getProductById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return convertToDto(product);
    }

    public ProductDto updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        if (existingProduct != null) {
            if(updatedProduct.getPrice() < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            return convertToDto(productRepository.save(existingProduct));
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }
}
