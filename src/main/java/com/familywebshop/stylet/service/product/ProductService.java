package com.familywebshop.stylet.service.product;

import com.familywebshop.stylet.dto.product.ProductDto;
import com.familywebshop.stylet.model.product.Product;

import java.util.List;

public interface ProductService {

    ProductDto getProduct(Long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByParams(List<String> colors, String size, String sortBy, double minPrice, double maxPrice);

    List<ProductDto> getProductsByCategoryAndParams(Long categoryId, List<String> colors, String size, String sortBy, double minPrice, double maxPrice);

    List<ProductDto> getAllProductsByRootCategoryName(String rootCategoryName, List<String> colors, String size, String sortBy, double minPrice, double maxPrice);

    void saveProduct(ProductDto productDto);

    void saveAllProducts(List<ProductDto> productDtoList);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    void deleteProduct(Long id);

    void deleteAllProducts();

    void reduceQuantity(Product product, String size, int quantity);
}
