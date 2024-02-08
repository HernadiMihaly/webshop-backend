package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.CategoryDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.service.CategoryService;
import com.familywebshop.stylet.util.FieldUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final FieldUpdater fieldUpdater;

    @Override
    public CategoryDto getCategory(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return mapEntityToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return mapEntityListToDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getParentCategory(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        Category parentCategory = (category.getParent() != null) ? category.getParent() : null;

        return (parentCategory != null) ? mapEntityToDto(parentCategory) : null;
    }

    @Override
    public List<CategoryDto> getSubCategories(Long id) throws CategoryNotFoundException {
        List<Category> subCategories = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id))
                .getSubCategories();

        return mapEntityListToDtoList(subCategories);
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) throws CategoryNotFoundException{
        categoryRepository.save(mapDtoToEntity(categoryDto));
    }

    @Override
    public void saveAllCategories(List<CategoryDto> categoryDtoList) throws CategoryNotFoundException{
        categoryRepository.saveAll(mapDtoListToEntityList(categoryDtoList));
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (categoryDto.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException(categoryDto.getParentId()));

            category.setParent(parentCategory);
        }

        fieldUpdater.updateFieldIfNotNull(categoryDto.getName(), category::setName);

        categoryRepository.save(category);

        return mapEntityToDto(category);
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException{
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new CategoryNotFoundException(id);
        }
    }

    @Override
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

    private Category mapDtoToEntity(CategoryDto categoryDto) throws CategoryNotFoundException{
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .parent(getParent(categoryDto.getParentId()))
                .build();
    }

    private Category getParent(Long parentId) throws CategoryNotFoundException {
        if (parentId == null) {
            return null;
        }

        return categoryRepository.findById(parentId)
                        .orElseThrow(() -> new CategoryNotFoundException(parentId));
    }

    private CategoryDto mapEntityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .build();
    }

    private List<Category> mapDtoListToEntityList(List<CategoryDto> categoryDtoList) throws CategoryNotFoundException{
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
