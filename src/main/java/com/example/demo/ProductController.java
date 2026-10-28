package com.example.demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.dto.ProductDto;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@RestController 
@RequestMapping ("/api/products")
public class ProductController {
private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")    
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);    
    }

    @DeleteMapping ("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping ("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {  
        return productService.updateProduct(id, updatedProduct);
    }

    @GetMapping
    public java.util.List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
    
}
