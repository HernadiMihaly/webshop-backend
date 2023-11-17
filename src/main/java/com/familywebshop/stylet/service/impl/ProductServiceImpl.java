package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductDTO;
import com.familywebshop.stylet.repository.ProductRepository;
import com.familywebshop.stylet.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProducts(){
        productRepository.findAll();
        //TODO: ModelMapper
        // https://auth0.com/blog/automatically-mapping-dto-to-entity-on-spring-boot-apis/
        return null;
    }


}
