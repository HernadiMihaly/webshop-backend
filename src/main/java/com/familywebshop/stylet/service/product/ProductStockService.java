package com.familywebshop.stylet.service.product;

import com.familywebshop.stylet.dto.product.ProductStockDto;
import com.familywebshop.stylet.model.product.Product;

import java.util.List;

public interface ProductStockService {

    ProductStockDto update(Long id, ProductStockDto productStockDto, Product product);

    List<ProductStockDto> updateAll(List<ProductStockDto> productStockDtoList, Product product);
}
