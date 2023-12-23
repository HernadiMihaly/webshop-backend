package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.ProductDto;
import com.familywebshop.stylet.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id){
        ProductDto productDto = productService.getProduct(id);

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/products/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("id") Long id){
        List<ProductDto> productDtos = productService.getProductsByCategory(id);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    //*TODO: INNENTŐL /admin kell majd minden elé!! CSAK ÁTMENETILEG LETT KIVÉVE
    @PostMapping("/products")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);

        return ResponseEntity.ok("Product added successfully.");
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok("Product successfully deleted!");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

}
