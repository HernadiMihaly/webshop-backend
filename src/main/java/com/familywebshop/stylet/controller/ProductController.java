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

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id){
        ProductDto productDto = productService.getProduct(id);

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        try {
            List<ProductDto> products = productService.getAllProducts();

            return new ResponseEntity<>(products, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            List<ProductDto> products = productService
                    .getAllProductsByParams(colors, size, sortBy, minPrice, maxPrice);

            return new ResponseEntity<>(products, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryAndParams(
            @PathVariable("id") Long id,
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            List<ProductDto> productDtos = productService
                    .getProductsByCategoryAndParams(id, colors, size, sortBy, minPrice, maxPrice);

            return new ResponseEntity<>(productDtos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/category/name/male")
    public ResponseEntity<List<ProductDto>> getAllMaleProductsByParams(
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            return new ResponseEntity<>(productService.getAllProductsByRootCategoryName(
                                    "férfi", colors, size, sortBy, minPrice, maxPrice
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/category/name/female")
    public ResponseEntity<List<ProductDto>> getAllFemaleProductsByParams(
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            return new ResponseEntity<>(productService.getAllProductsByRootCategoryName(
                    "női", colors, size, sortBy, minPrice, maxPrice
            ),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/category/name/children")
    public ResponseEntity<List<ProductDto>> getAllChildrenProductsByParams(
            @RequestParam List<String> colors,
            @RequestParam String size,
            @RequestParam String sortBy,
            @RequestParam  double minPrice,
            @RequestParam double maxPrice
    ){
        try {
            return new ResponseEntity<>(productService.getAllProductsByRootCategoryName(
                    "gyerek", colors, size, sortBy, minPrice, maxPrice
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //*TODO: INNENTŐL /admin kell majd minden elé!! CSAK ÁTMENETILEG LETT KIVÉVE
    @PostMapping("/products")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);

        return ResponseEntity.ok("Product added successfully.");
    }

    @PostMapping("/products/all")
    public ResponseEntity<String> saveAllProducts(@RequestBody List<ProductDto> productDtoList) {
        productService.addAllProducts(productDtoList);

        return ResponseEntity.ok("Products added successfully.");
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
