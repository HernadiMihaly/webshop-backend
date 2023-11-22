package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.CategoryDto;
import com.familywebshop.stylet.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id){
        CategoryDto categoryDto = categoryService.getCategory(id);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/categories/subcategories/{id}")
    public ResponseEntity<List<CategoryDto>> getSubCategories(@PathVariable Long id){
        List<CategoryDto> subCategories = categoryService.getSubCategories(id);

        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }


    @PostMapping("/admin/categories")
    public ResponseEntity<String> saveCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.saveCategory(categoryDto);

        return ResponseEntity.ok("Category added successfully.");
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);

        return ResponseEntity.ok("Category deleted successfully!");
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {

        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK );
    }

}
