package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.CategoryDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(CategoryDto categoryDto) {
        categoryRepository.save(mapDtoToEntity(categoryDto));
    }

    @Override
    public void addAllCategories(List<CategoryDto> categoryDtoList) {
        categoryRepository.saveAll(mapDtoListToEntityList(categoryDtoList));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return mapEntityListToDtoList(categoryRepository.findAll());
    }

    @Override
    public List<CategoryDto> getSubCategories(Long id) {
        List<Category> categories = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"))
                .getSubCategories();

        return mapEntityListToDtoList(categories);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        Optional<Category> parentCategory = categoryRepository.findById(categoryDto.getParentId());

        if (parentCategory.isPresent()){
            category.setParent(parentCategory.get());
        } else {
            category.setParent(null);
        }

        category.setName(categoryDto.getName());

        categoryRepository.save(category);

        return mapEntityToDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        return mapEntityToDto(category);
    }

    @Override
    public CategoryDto getParentCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        try {
            Category parentCategory = category.getParent();

            return mapEntityToDto(parentCategory);
        } catch (Exception e){
            throw new CategoryNotFoundException("There's no parent of root category: " + id);
        }
    }

    private Category mapDtoToEntity(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .parent(mapDtoToEntity(categoryDto.getParentId()))
                .build();

    }

    private CategoryDto mapEntityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .build();
    }

    private Category mapDtoToEntity(Long parentId) {
        if (parentId == null) {
            return null;
        }
        Category parentCategory = new Category();
        parentCategory.setId(parentId);
        return parentCategory;
    }

    private List<Category> mapDtoListToEntityList(List<CategoryDto> categoryDtoList) {
        if (categoryDtoList == null) return null;

        return categoryDtoList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    private List<CategoryDto> mapEntityListToDtoList(List<Category> categoryList) {
        return categoryList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
