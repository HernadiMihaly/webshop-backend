package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.exception.ProductNotFoundException;
import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.repository.ProductRepository;
import com.familywebshop.stylet.service.ProductService;
import com.familywebshop.stylet.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addProduct(ProductDto productDto) {
        productRepository.save(ModelMapper.getInstance()
                .getModelMapper()
                .map(productDto, Product.class));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return getDtoListFromProductList(products);
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        List<Product> productsByCategory = getAllProductsFromCategory(category);


        return getDtoListFromProductList(productsByCategory);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setName(productDto.getName());
            existingProduct.setColor(productDto.getColor());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setMaterials(productDto.getMaterials());
            existingProduct.setImageUrl(productDto.getImageUrl());

            productRepository.save(existingProduct);

            return ModelMapper.getInstance().getModelMapper().map(existingProduct, ProductDto.class);
        } else {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
    }

    @Override
    public ProductDto getProduct(Long id) {
        return ModelMapper.getInstance()
                .getModelMapper()
                .map(productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException("Product does not exist!")),
                        ProductDto.class
                );
    }

    private List<Product> getAllProductsFromCategory(Category category) {
        List<Product> productsByCategory = new ArrayList<>(category.getProducts());

        for (Category subCategory : category.getSubCategories()) {
            productsByCategory.addAll(getAllProductsFromCategory(subCategory));
        }

        return productsByCategory;
    }

    private List<ProductDto> getDtoListFromProductList(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products){
            productDtos.add(ModelMapper.getInstance()
                    .getModelMapper()
                    .map(product, ProductDto.class));
        }

        return productDtos;
    }

}
