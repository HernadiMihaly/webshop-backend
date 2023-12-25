package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    void addProduct(ProductDto productDto);

    List<ProductDto> getProductsByCategory(Long id);

    void deleteProduct(Long id);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    ProductDto getProduct(Long id);

    void addAllProducts(List<ProductDto> productDtoList);

    List<ProductDto> getAllProductsByRootCategoryName(String rootCategoryName);
}
