package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductDto;
import com.familywebshop.stylet.dto.ProductPhotoDto;
import com.familywebshop.stylet.dto.ProductStockDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.exception.ProductNotFoundException;
import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import com.familywebshop.stylet.model.ProductPhoto;
import com.familywebshop.stylet.model.ProductStock;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.repository.ProductRepository;
import com.familywebshop.stylet.service.ProductPhotoService;
import com.familywebshop.stylet.service.ProductService;
import com.familywebshop.stylet.service.ProductStockService;
import com.familywebshop.stylet.util.FieldUpdater;
import com.familywebshop.stylet.util.ModelMapper;
import com.familywebshop.stylet.util.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductStockService productStockService;

    private final ProductPhotoService productPhotoService;

    private final FieldUpdater fieldUpdater;

    @Override
    public ProductDto getProduct(Long id) throws ProductNotFoundException{
        return mapEntityToDto(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return mapEntityListToDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getAllProductsByParams(List<String> colors, String size, String sortBy, double minPrice, double maxPrice) {
        return mapEntityListToDtoList(productRepository
                .findAll(getSpecification(
                                colors, getSizeFromParam(size), minPrice, maxPrice
                        ),
                        getSortRuleFromParam(sortBy)));
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndParams(Long categoryId, List<String> colors, String size, String sortBy, double minPrice, double maxPrice){
        List<Product> productsByCategory = productRepository
                .findAll(getSpecificationWithCategoryId(categoryId, colors, getSizeFromParam(size), minPrice, maxPrice),
                        getSortRuleFromParam(sortBy)
                );

        return mapEntityListToDtoList(productsByCategory);
    }

    @Override
    public List<ProductDto> getAllProductsByRootCategoryName(String rootCategoryName, List<String> colors, String size, String sortBy, double minPrice, double maxPrice) {
        return mapEntityListToDtoList(
                productRepository.findAll(
                        getSpecificationWithRootCategoryName(
                                rootCategoryName.toLowerCase(), colors, getSizeFromParam(size), minPrice, maxPrice
                        ),
                        getSortRuleFromParam(sortBy)
                )
        );
    }

    @Override
    public void saveProduct(ProductDto productDto) throws CategoryNotFoundException{
        productRepository.save(mapDtoToEntity(productDto));
    }

    @Override
    public void saveAllProducts(List<ProductDto> productDtoList) throws CategoryNotFoundException{
        productRepository.saveAll(mapDtoListToEntityList(productDtoList));
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws ProductNotFoundException, CategoryNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        fieldUpdater.updateFieldIfNotNull(productDto.getName(), product::setName);
        fieldUpdater.updateFieldIfNotNull(productDto.getColor(), product::setColor);
        fieldUpdater.updateFieldIfNotNull(productDto.getDescription(), product::setDescription);
        fieldUpdater.updateFieldIfNotNull(productDto.getMaterials(), product::setMaterials);
        fieldUpdater.updateFieldIfNotNull(productDto.getPrice(), product::setPrice);
        updateCategory(productDto.getCategory(), product);

        updateProductStocks(productDto.getProductStocks(), product);
        updateProductPhotos(productDto.getProductPhotos(), product);

        productRepository.save(product);

        return mapEntityToDto(product);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException{
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    private List<Product> mapDtoListToEntityList(List<ProductDto> productDtoList) throws CategoryNotFoundException{
        return productDtoList
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    private List<ProductDto> mapEntityListToDtoList(List<Product> productList) {
        return productList
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private Product mapDtoToEntity(ProductDto productDto) throws CategoryNotFoundException {
        Product product = Product.builder()
                .name(productDto.getName())
                .color(productDto.getColor())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .materials(productDto.getMaterials())
                .category(Optional.ofNullable(productDto.getCategory())
                        .map(categoryId -> categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new CategoryNotFoundException(categoryId)))
                        .orElseThrow(() -> new CategoryNotFoundException("Category ID is null")))
                .productStocks(Optional.ofNullable(productDto.getProductStocks())
                        .map(productStocks -> ModelMapper.getInstance().mapDtoListToEntityList(productStocks, ProductStock.class))
                        .orElse(Collections.emptyList()))
                .productPhotos(Optional.ofNullable(productDto.getProductPhotos())
                        .map(productPhotos -> ModelMapper.getInstance().mapDtoListToEntityList(productPhotos, ProductPhoto.class))
                        .orElse(Collections.emptyList()))
                .build();

        createRelationships(product);

        return product;
    }

    private ProductDto mapEntityToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .color(product.getColor())
                .price(product.getPrice())
                .description(product.getDescription())
                .materials(product.getMaterials())
                .category(product.getCategory().getId())
                .createdAt(product.getCreatedAt())
                .productStocks(ModelMapper.getInstance()
                        .mapEntityListToDtoList(product.getProductStocks(), ProductStockDto.class))
                .productPhotos(ModelMapper.getInstance()
                        .mapEntityListToDtoList(product.getProductPhotos(), ProductPhotoDto.class))
                .build();
    }

    private void createRelationships(Product product){
        if (product.getProductStocks() != null && product.getProductPhotos() != null) {
            for (int i = 0; i < product.getProductPhotos().size(); i++) {
                product.getProductPhotos().get(i).setProduct(product);
            }
            for (int i = 0; i < product.getProductStocks().size(); i++) {
                product.getProductStocks().get(i).setProduct(product);
            }
        }
    }

    private Specification<Product> getSpecificationWithCategoryId(Long categoryId, List<String> colors, String size, double minPrice, double maxPrice){
        return getSpecification(colors, size, minPrice, maxPrice)
                .and(ProductSpecification.hasCategoryId(categoryId));
    }

    private Specification<Product> getSpecificationWithRootCategoryName(String rootCategoryName, List<String> colors, String size, double minPrice, double maxPrice){
        return getSpecification(colors, size, minPrice, maxPrice)
                .and(ProductSpecification.hasRootCategoryName(rootCategoryName));
    }

    private Specification<Product> getSpecification(List<String> colors, String size, double minPrice, double maxPrice){
        return Specification
                .where(ProductSpecification.hasSize(size))
                .and(ProductSpecification.hasColorIn(colors))
                .and(ProductSpecification.priceBetween(minPrice, maxPrice));
    }

    private Sort getSortRuleFromParam(String sortBy){
        return switch (sortBy){
            case "ascending" -> Sort.by(Sort.Order.asc("price"));
            case "descending" -> Sort.by(Sort.Order.desc("price"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };
    }

    private String getSizeFromParam(String size){
        size = size.toLowerCase();

        if (size.contains("eu") && size.split(" ").length>1){
            String[] sizes = size.split(" ");

            return sizes[1];
        } else {
            return size;
        }
    }

    private void updateCategory(Long categoryId, Product product) throws CategoryNotFoundException {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId));
            product.setCategory(category);
        }
    }

    private void updateProductStocks(List<ProductStockDto> productStocks, Product product) {
        if (productStocks != null) {
            productStockService.updateAll(productStocks, product);
        }
    }

    private void updateProductPhotos(List<ProductPhotoDto> productPhotos, Product product) {
        if (productPhotos != null) {
            productPhotoService.updateAll(productPhotos, product);
        }
    }
}
