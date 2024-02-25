package com.familywebshop.stylet.service.product;

import com.familywebshop.stylet.dto.product.ProductPhotoDto;
import com.familywebshop.stylet.model.product.Product;

import java.util.List;

public interface ProductPhotoService {

    ProductPhotoDto update(Long id, ProductPhotoDto productPhotoDto, Product product);

    List<ProductPhotoDto> updateAll(List<ProductPhotoDto> productPhotoDtoList, Product product);
}
