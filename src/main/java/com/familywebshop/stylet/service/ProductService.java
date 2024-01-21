package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProductsByParams(List<String> colors, String size, String sortBy, double minPrice, double maxPrice);

    List<ProductDto> getAllProducts();

    void addProduct(ProductDto productDto);

    List<ProductDto> getProductsByCategoryAndParams(Long id, List<String> colors, String size, String sortBy, double minPrice, double maxPrice);

    void deleteProduct(Long id);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    ProductDto getProduct(Long id);

    void addAllProducts(List<ProductDto> productDtoList);

    List<ProductDto> getAllProductsByRootCategoryName(String rootCategoryName, List<String> colors, String size, String sortBy, double minPrice, double maxPrice);

    void deleteAllProducts();

}
