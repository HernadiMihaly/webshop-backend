package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.ProductStockDto;
import com.familywebshop.stylet.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductStockService {

    ProductStockDto update(Long id, ProductStockDto productStockDto, Product product);

    List<ProductStockDto> updateAll(List<ProductStockDto> productStockDtoList, Product product);
}
