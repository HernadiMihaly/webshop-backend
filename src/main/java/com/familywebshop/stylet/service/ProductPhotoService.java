package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.ProductPhotoDto;
import com.familywebshop.stylet.model.Product;

import java.util.List;

public interface ProductPhotoService {

    ProductPhotoDto update(Long id, ProductPhotoDto productPhotoDto, Product product);

    List<ProductPhotoDto> updateAll(List<ProductPhotoDto> productPhotoDtoList, Product product);

}
