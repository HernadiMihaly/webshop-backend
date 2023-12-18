package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductStockDto;
import com.familywebshop.stylet.model.ProductStock;
import com.familywebshop.stylet.repository.ProductStockRepository;
import com.familywebshop.stylet.service.ProductStockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStockServiceImpl implements ProductStockService {

    private final ProductStockRepository productStockRepository;

    public ProductStockServiceImpl(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Override
    public List<ProductStockDto> updateAll(List<ProductStockDto> productStockDtoList){
        for (ProductStockDto productStockDto : productStockDtoList){
            update(productStockDto.getId(), productStockDto);
        }

        return productStockDtoList;

    }

    @Override
    public ProductStockDto update(Long id, ProductStockDto productStockDto){
        ProductStock productStock = productStockRepository.findById(id)
                .orElseThrow(); //TODO: ProductStockNotFoundException

        productStock.setQuantity(productStockDto.getQuantity());
        productStock.setSize(productStockDto.getSize());

        productStockRepository.save(productStock);

        return productStockDto;

    }

}
