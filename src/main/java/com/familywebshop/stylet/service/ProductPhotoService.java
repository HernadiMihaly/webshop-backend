package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.ProductPhotoDto;

import java.util.List;

public interface ProductPhotoService {

    ProductPhotoDto update(Long id, ProductPhotoDto productPhotoDto);

    List<ProductPhotoDto> updateAll(List<ProductPhotoDto> productPhotoDtoList);

}
