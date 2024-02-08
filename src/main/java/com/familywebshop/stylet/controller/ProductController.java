package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.ProductDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.exception.ProductNotFoundException;
import com.familywebshop.stylet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        try {
            ProductDto productDto = productService.getProduct(id);

            return ResponseEntity.ok(productDto);
        } catch (ProductNotFoundException productNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(productNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        try {
            List<ProductDto> products = productService.getAllProducts();

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products/byParams")
    public ResponseEntity<?> getAllProductsByParams(
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            List<ProductDto> products = productService.getAllProductsByParams(colors, size, sortBy, minPrice, maxPrice);

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products/category/{id}")
    public ResponseEntity<?> getProductsByCategoryAndParams(
            @PathVariable("id") Long categoryId,
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            List<ProductDto> productDtoList = productService
                    .getProductsByCategoryAndParams(categoryId, colors, size, sortBy, minPrice, maxPrice);

            return ResponseEntity.ok(productDtoList);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products/category/name/male")
    public ResponseEntity<?> getAllMaleProductsByParams(
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            return ResponseEntity.ok(productService.getAllProductsByRootCategoryName(
                    "férfi", colors, size, sortBy, minPrice, maxPrice
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products/category/name/female")
    public ResponseEntity<?> getAllFemaleProductsByParams(
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            return ResponseEntity.ok(productService.getAllProductsByRootCategoryName(
                    "női", colors, size, sortBy, minPrice, maxPrice
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products/category/name/children")
    public ResponseEntity<?> getAllChildrenProductsByParams(
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam  double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            return ResponseEntity.ok(productService.getAllProductsByRootCategoryName(
                    "gyerek", colors, size, sortBy, minPrice, maxPrice
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //*TODO: INNENTŐL /admin kell majd minden elé!! CSAK ÁTMENETILEG LETT KIVÉVE
    @PostMapping("/products")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto) {
        try {
            productService.saveProduct(productDto);

            return ResponseEntity.ok("Product successfully added.");
        } catch (IllegalArgumentException illegalArgumentException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid argument: " + illegalArgumentException.getMessage());
        } catch (CategoryNotFoundException categoryNotFoundException){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(categoryNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/products/all")
    public ResponseEntity<?> saveAllProducts(@RequestBody List<ProductDto> productDtoList) {
        try {
            productService.saveAllProducts(productDtoList);

            return ResponseEntity.ok("All products successfully added.");
        } catch (IllegalArgumentException illegalArgumentException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid argument: " + illegalArgumentException.getMessage());
        } catch (CategoryNotFoundException categoryNotFoundException){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(categoryNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.updateProduct(id, productDto);

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFoundException productNotFoundException){
            return ResponseEntity.badRequest().body(productNotFoundException.getMessage());
        } catch (CategoryNotFoundException categoryNotFoundException){
            return ResponseEntity.badRequest().body(categoryNotFoundException.getMessage());
        } catch (Exception exception){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProduct(id);

            return ResponseEntity.ok("Product successfully deleted!");
        } catch (ProductNotFoundException productNotFoundException){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(productNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/products")
    public ResponseEntity<?> deleteAllProducts() {
        try {
            productService.deleteAllProducts();

            return ResponseEntity.ok("All products successfully deleted!");
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
