package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.CategoryDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.service.CategoryService;
import com.familywebshop.stylet.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        categoryRepository.save(ModelMapper.getInstance()
                .mapDtoToEntity(categoryDto, Category.class));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return ModelMapper.getInstance()
                .mapEntityListToDtoList(categoryRepository.findAll(), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getSubCategories(Long id) {
        List<Category> categories = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"))
                .getSubCategories();

        return ModelMapper.getInstance()
                .mapEntityListToDtoList(categories, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        category.setParentCategory(categoryDto.getParentCategory());
        category.setName(categoryDto.getName());

        categoryRepository.save(category);

        return ModelMapper.getInstance()
                .mapEntityToDto(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        return ModelMapper.getInstance()
                .mapEntityToDto(category, CategoryDto.class);
    }

}
