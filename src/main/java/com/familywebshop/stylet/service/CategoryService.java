package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    void saveCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    List<CategoryDto> getSubCategories(Long id);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto getCategory(Long id);
}