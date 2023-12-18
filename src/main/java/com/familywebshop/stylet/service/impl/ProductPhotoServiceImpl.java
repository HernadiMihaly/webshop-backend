package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductPhotoDto;
import com.familywebshop.stylet.model.ProductPhoto;
import com.familywebshop.stylet.repository.ProductPhotoRepository;
import com.familywebshop.stylet.service.ProductPhotoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPhotoServiceImpl implements ProductPhotoService {

    private final ProductPhotoRepository productPhotoRepository;

    public ProductPhotoServiceImpl(ProductPhotoRepository productPhotoRepository) {
        this.productPhotoRepository = productPhotoRepository;
    }

    @Override
    public List<ProductPhotoDto> updateAll(List<ProductPhotoDto> productPhotoDtoList){
        for (ProductPhotoDto productPhotoDto : productPhotoDtoList){
            update(productPhotoDto.getId(), productPhotoDto);
        }

        return productPhotoDtoList;

    }

    @Override
    public ProductPhotoDto update(Long id, ProductPhotoDto productPhotoDto){
        ProductPhoto productPhoto = productPhotoRepository.findById(id)
                .orElseThrow(); //TODO: ProductPhotoNotFoundException

        productPhoto.setImageUrl(productPhotoDto.getImageUrl());

        productPhotoRepository.save(productPhoto);

        return productPhotoDto;

    }

}
