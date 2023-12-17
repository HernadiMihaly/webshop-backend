package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.CategoryDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.service.CategoryService;
import com.familywebshop.stylet.util.ModelMapper;
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
    public void saveCategory(CategoryDto categoryDto) {
        categoryRepository.save(mapDtoToEntity(categoryDto));
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
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        return mapEntityToDto(category);
    }

    /* Sajnos az egymásba ágyazott CategoryDto-k és a parentCategoryId miatt ennél az osztálynál
    saját map-elést kell létrehoznunk és nem tudjuk használni a ModelMapper singleton-t.
     */
    private static Category mapDtoToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setParent(mapDtoToEntity(categoryDto.getParentId()));
        category.setSubCategories(mapDtoListToEntityList(categoryDto.getSubCategories()));
        return category;
    }

    private static CategoryDto mapEntityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setParentId(category.getParent() != null ? category.getParent().getId() : null);
        categoryDto.setSubCategories(mapEntityListToDtoList(category.getSubCategories()));
        return categoryDto;
    }

    private static Category mapDtoToEntity(Long parentId) {
        if (parentId == null) {
            return null;
        }
        Category parentCategory = new Category();
        parentCategory.setId(parentId);
        return parentCategory;
    }

    private static List<Category> mapDtoListToEntityList(List<CategoryDto> categoryDtoList) {
        return categoryDtoList.stream()
                .map(CategoryServiceImpl::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    private static List<CategoryDto> mapEntityListToDtoList(List<Category> categoryList) {
        return categoryList.stream()
                .map(CategoryServiceImpl::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
