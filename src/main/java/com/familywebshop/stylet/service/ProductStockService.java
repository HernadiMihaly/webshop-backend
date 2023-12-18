package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.ProductStockDto;
import com.familywebshop.stylet.model.ProductStock;

import java.util.List;

public interface ProductStockService {

    ProductStockDto update(Long id, ProductStockDto productStockDto);

    List<ProductStockDto> updateAll(List<ProductStockDto> productStockDtoList);

}
