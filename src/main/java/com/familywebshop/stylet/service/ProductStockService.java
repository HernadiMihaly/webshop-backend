package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.ProductStockDto;

import java.util.List;
import java.util.Set;

public interface ProductStockService {

    ProductStockDto update(Long id, ProductStockDto productStockDto);

    List<ProductStockDto> updateAll(List<ProductStockDto> productStockDtoList);

}
