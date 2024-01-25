package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto getParentCategory(Long id);

    List<CategoryDto> getSubCategories(Long id);

    void saveCategory(CategoryDto categoryDto);

    void saveAllCategories(List<CategoryDto> categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);

    void deleteAllCategories();
    
}
