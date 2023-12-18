package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductDto;
import com.familywebshop.stylet.dto.ProductPhotoDto;
import com.familywebshop.stylet.dto.ProductStockDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.exception.ProductNotFoundException;
import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import com.familywebshop.stylet.model.ProductPhoto;
import com.familywebshop.stylet.model.ProductStock;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.repository.ProductRepository;
import com.familywebshop.stylet.service.ProductPhotoService;
import com.familywebshop.stylet.service.ProductService;
import com.familywebshop.stylet.service.ProductStockService;
import com.familywebshop.stylet.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductStockService productStockService;

    private final ProductPhotoService productPhotoService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductStockService productStockService, ProductPhotoService productPhotoService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productStockService = productStockService;
        this.productPhotoService = productPhotoService;
    }

    @Override
    public void addProduct(ProductDto productDto) {
        productRepository.save(mapDtoToEntity(productDto));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return mapEntityListToDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist!"));

        List<Product> productsByCategory = getAllProductsFromCategory(category);


        return mapEntityListToDtoList(productsByCategory);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist!"));

        product.setName(productDto.getName());
        product.setColor(productDto.getColor());
        product.setDescription(productDto.getDescription());
        product.setMaterials(productDto.getMaterials());
        product.setPrice(productDto.getPrice());
        product.setCategory(
                categoryRepository.findById(productDto.getCategory())
                        .orElseThrow(() -> new CategoryNotFoundException(
                                "Cannot set to product's category, because no such category!"
                        ))
        );

        productStockService.updateAll(productDto.getProductStock());
        productPhotoService.updateAll(productDto.getProductPhotos());

        productRepository.save(product);

        return productDto;
    }

    @Override
    public ProductDto getProduct(Long id) {
        return mapEntityToDto(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist!")));
    }

    private List<Product> getAllProductsFromCategory(Category category) {
        List<Product> productsByCategory = new ArrayList<>(category.getProducts());

        for (Category subCategory : category.getSubCategories()) {
            productsByCategory.addAll(getAllProductsFromCategory(subCategory));
        }

        return productsByCategory;
    }

    private Product mapDtoToEntity(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .color(productDto.getColor())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .materials(productDto.getMaterials())
                .category(categoryRepository.findById(productDto.getCategory())
                        .orElseThrow(() -> new CategoryNotFoundException("The products category not found!")))
                .productStock(ModelMapper.getInstance()
                        .mapDtoListToEntityList(productDto.getProductStock(), ProductStock.class))
                .productPhotos(ModelMapper.getInstance()
                        .mapDtoListToEntityList(productDto.getProductPhotos(), ProductPhoto.class))
                .build();

        createRelationships(product);

        return product;

    }

    private ProductDto mapEntityToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .color(product.getColor())
                .price(product.getPrice())
                .description(product.getDescription())
                .materials(product.getMaterials())
                .category(product.getCategory().getId())
                .productStock(ModelMapper.getInstance()
                        .mapEntityListToDtoList(product.getProductStock(), ProductStockDto.class))
                .productPhotos(ModelMapper.getInstance()
                        .mapEntityListToDtoList(product.getProductPhotos(), ProductPhotoDto.class))
                .build();
    }

    private List<ProductDto> mapEntityListToDtoList(List<Product> productList) {
        return productList
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private void createRelationships(Product product){
        for(int i=0; i<product.getProductPhotos().size(); i++) {
            product.getProductPhotos().get(i).setProduct(product);
        }
        for (int i=0; i<product.getProductStock().size(); i++){
            product.getProductStock().get(i).setProduct(product);
        }
    }

}
