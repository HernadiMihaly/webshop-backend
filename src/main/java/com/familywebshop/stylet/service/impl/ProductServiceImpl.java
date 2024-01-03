package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.ProductDto;
import com.familywebshop.stylet.dto.ProductPhotoDto;
import com.familywebshop.stylet.dto.ProductStockDto;
import com.familywebshop.stylet.exception.CategoryNotFoundException;
import com.familywebshop.stylet.exception.ProductNotFoundException;
import com.familywebshop.stylet.model.Product;
import com.familywebshop.stylet.model.ProductPhoto;
import com.familywebshop.stylet.model.ProductStock;
import com.familywebshop.stylet.repository.CategoryRepository;
import com.familywebshop.stylet.repository.ProductRepository;
import com.familywebshop.stylet.service.ProductPhotoService;
import com.familywebshop.stylet.service.ProductService;
import com.familywebshop.stylet.service.ProductStockService;
import com.familywebshop.stylet.util.ModelMapper;
import com.familywebshop.stylet.util.ProductSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public ProductDto getProduct(Long id) {
        return mapEntityToDto(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist!")));
    }

    @Override
    public List<ProductDto> getAllProductsByParams(List<String> colors, String size, String sortBy, double minPrice, double maxPrice) {
        return mapEntityListToDtoList(productRepository
                .findAll(getSpecification(
                                colors, getSizeFromParam(size), minPrice, maxPrice
                        ),
                        getSortRuleFromParam(sortBy)));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return mapEntityListToDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndParams(Long categoryId, List<String> colors, String size, String sortBy, double minPrice, double maxPrice){
        List<Product> productsByCategory = productRepository
                .findAll(getSpecificationWithCategoryId(
                                categoryId, colors, getSizeFromParam(size), minPrice, maxPrice
                        ),
                        getSortRuleFromParam(sortBy));

        return mapEntityListToDtoList(productsByCategory);
    }

    @Override
    public List<ProductDto> getAllProductsByRootCategoryName(String rootCategoryName, List<String> colors, String size, String sortBy, double minPrice, double maxPrice) {
        return mapEntityListToDtoList(
                productRepository.findAll(
                        getSpecificationWithRootCategoryName(
                                rootCategoryName.toLowerCase(), colors, getSizeFromParam(size), minPrice, maxPrice
                        ),
                        getSortRuleFromParam(sortBy)
                )
        );
    }

    @Override
    public void addProduct(ProductDto productDto) {
        productRepository.save(mapDtoToEntity(productDto));
    }

    @Override
    public void addAllProducts(List<ProductDto> productDtoList) {
        productRepository.saveAll(mapDtoListToEntityList(productDtoList));
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

        productStockService.updateAll(productDto.getProductStocks());
        productPhotoService.updateAll(productDto.getProductPhotos());

        productRepository.save(product);

        return productDto;
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
                .productStocks(ModelMapper.getInstance()
                        .mapDtoListToEntityList(productDto.getProductStocks(), ProductStock.class))
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
                .createdAt(product.getCreatedAt())
                .productStocks(ModelMapper.getInstance()
                        .mapEntityListToDtoList(product.getProductStocks(), ProductStockDto.class))
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

    private List<Product> mapDtoListToEntityList(List<ProductDto> productDtoList) {
        return productDtoList
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    private void createRelationships(Product product){
        for(int i=0; i<product.getProductPhotos().size(); i++) {
            product.getProductPhotos().get(i).setProduct(product);
        }
        for (int i = 0; i<product.getProductStocks().size(); i++){
            product.getProductStocks().get(i).setProduct(product);
        }
    }

    private Specification<Product> getSpecificationWithCategoryId(Long categoryId, List<String> colors, String size, double minPrice, double maxPrice){
        return getSpecification(colors, size, minPrice, maxPrice)
                .and(ProductSpecification.hasCategoryId(categoryId));
    }

    private Specification<Product> getSpecificationWithRootCategoryName(String rootCategoryName, List<String> colors, String size, double minPrice, double maxPrice){
        return getSpecification(colors, size, minPrice, maxPrice)
                .and(ProductSpecification.hasRootCategoryName(rootCategoryName));
    }

    private Specification<Product> getSpecification(List<String> colors, String size, double minPrice, double maxPrice){
        return Specification
                .where(ProductSpecification.hasSize(size))
                .and(ProductSpecification.hasColorIn(colors))
                .and(ProductSpecification.priceBetween(minPrice, maxPrice));
    }

    private Sort getSortRuleFromParam(String sortBy){
        return switch (sortBy){
            case "ascending" -> Sort.by(Sort.Order.asc("price"));
            case "descending" -> Sort.by(Sort.Order.desc("price"));
            default -> Sort.by(Sort.Order.desc("createdAt"));
        };
    }

    private String getSizeFromParam(String size){
        size = size.toLowerCase();

        if (size.contains("eu")){
            String[] sizes = size.split(" ");

            return sizes[1];
        } else {
            return size;
        }
    }

}
