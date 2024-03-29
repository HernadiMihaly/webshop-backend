package com.familywebshop.stylet.controller.product;

import com.familywebshop.stylet.dto.product.CategoryDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id){
        try {
            CategoryDto categoryDto = categoryService.getCategory(id);

            return ResponseEntity.ok(categoryDto);
        } catch (CategoryNotFoundException categoryNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(categoryNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories(){
        try {
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/categories/{id}/parent")
    public ResponseEntity<?> getParentCategory(@PathVariable Long id){
        try {
            CategoryDto categoryDto = categoryService.getParentCategory(id);

            return ResponseEntity.ok(categoryDto);
        } catch (CategoryNotFoundException categoryNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(categoryNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/categories/{id}/subcategories")
    public ResponseEntity<?> getSubCategories(@PathVariable Long id){
        try {
            List<CategoryDto> subCategories = categoryService.getSubCategories(id);

            return ResponseEntity.ok(subCategories);
        } catch (CategoryNotFoundException categoryNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(categoryNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    /*TODO: innetől a /admin-t majd elé kell helyezni később minden root elé
     */

    @PostMapping("/categories")
    public ResponseEntity<String> saveCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.saveCategory(categoryDto);

            return ResponseEntity.ok("Category successfully added.");
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

    @PostMapping("/categories/all")
    public ResponseEntity<String> saveAllCategories(@RequestBody List<CategoryDto> categoryDtoList) {
        try {
            categoryService.saveAllCategories(categoryDtoList);

            return ResponseEntity.ok("All categories successfully added.");
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

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
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

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);

            return ResponseEntity.ok("Category successfully deleted!");
        } catch (CategoryNotFoundException categoryNotFoundException){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(categoryNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/categories")
    public ResponseEntity<?> deleteAllCategories(){
        try {
            categoryService.deleteAllCategories();

            return ResponseEntity.ok("All categories successfully deleted!");
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
