package com.familywebshop.stylet.service.product.impl;

import com.familywebshop.stylet.dto.product.ProductStockDto;
import com.familywebshop.stylet.model.product.Product;
import com.familywebshop.stylet.model.product.ProductStock;
import com.familywebshop.stylet.repository.product.ProductStockRepository;
import com.familywebshop.stylet.service.product.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {

    private final ProductStockRepository productStockRepository;

    @Override
    public List<ProductStockDto> updateAll(List<ProductStockDto> productStockDtoList, Product product){
        for (ProductStockDto productStockDto : productStockDtoList){
            update(productStockDto.getId(), productStockDto, product);
        }

        return productStockDtoList;

    }

    @Override
    public ProductStockDto update(Long id, ProductStockDto productStockDto, Product product){
        if (id != null && productStockRepository.findById(id).isPresent()) {
            ProductStock productStock = productStockRepository.findById(id).get();

            productStock.setQuantity(productStockDto.getQuantity());
            productStock.setSize(productStockDto.getSize());
            productStock.setProduct(product);

            productStockRepository.save(productStock);
        } else {
            productStockRepository.save(ProductStock.builder()
                    .size(productStockDto.getSize())
                    .quantity(productStockDto.getQuantity())
                    .product(product)
                    .build()
            );
        }

        return productStockDto;
    }

}
