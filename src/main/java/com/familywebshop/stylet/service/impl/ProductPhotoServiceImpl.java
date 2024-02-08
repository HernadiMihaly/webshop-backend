package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductPhotoDto;
import com.familywebshop.stylet.model.Product;
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
    public List<ProductPhotoDto> updateAll(List<ProductPhotoDto> productPhotoDtoList, Product product){
        for (ProductPhotoDto productPhotoDto : productPhotoDtoList){
            update(productPhotoDto.getId(), productPhotoDto, product);
        }

        return productPhotoDtoList;

    }

    @Override
    public ProductPhotoDto update(Long id, ProductPhotoDto productPhotoDto, Product product){
        if (id != null && productPhotoRepository.findById(id).isPresent()) {
            ProductPhoto productPhoto = productPhotoRepository.findById(id).get();

            productPhoto.setImageUrl(productPhotoDto.getImageUrl());
            productPhoto.setProduct(product);

            productPhotoRepository.save(productPhoto);
        } else{
            productPhotoRepository.save(ProductPhoto.builder()
                    .imageUrl(productPhotoDto.getImageUrl())
                    .product(product)
                    .build()
            );
        }

        return productPhotoDto;
    }
}
