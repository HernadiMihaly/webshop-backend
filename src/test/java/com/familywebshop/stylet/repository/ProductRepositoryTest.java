package com.familywebshop.stylet.repository;

import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import com.familywebshop.stylet.model.ProductStock;
import com.familywebshop.stylet.util.ProductSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
public class ProductRepositoryTest {

    private static final String TEST = "test";
    private static final double TEST_PRICE = 0.0;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductStockRepository productStockRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository, CategoryRepository categoryRepository, ProductStockRepository productStockRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productStockRepository = productStockRepository;
    }

    // Color Filter
    @Test
    public void findAllWithSpecification_WhenColorIsEmpty_returnsFourProducts() {
        Category category = saveCategory(null);

        saveMultipleProducts(category, 4);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasColorIn(List.of()),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(4, products.size(), "Expected size 4, but actual size is " + products.size());
    }

    @Test
    public void findAllWithSpecification_WhenColorIsWhite_returnsThreeProducts() {
        Category category = saveCategory(null);

        saveProduct(TEST_PRICE, "Fehér/Törtfehér", category);
        saveProduct(TEST_PRICE, "Fekete", category);
        saveProduct(TEST_PRICE, "Fehér", category);
        saveProduct(TEST_PRICE, "fehér", category);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasColorIn(List.of("FEHÉR")),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(3, products.size(), "Expected size 3, but actual size is " + products.size());
    }

    @Test
    public void findAllWithSpecification_WhenColorInList_returnsTwoProducts() {
        Category category = saveCategory(null);

        saveProduct(TEST_PRICE, "Szürke/Törtfehér", category);
        saveProduct(TEST_PRICE, "törtfehér", category);
        saveProduct(TEST_PRICE, "bézs", category);
        saveProduct(TEST_PRICE, "kék", category);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasColorIn(List.of("törtfehér", "zöld", "fekete", "piros")),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(2, products.size(), "Expected size 2, but actual size is " + products.size());
    }

    @Test
    public void findAllWithSpecification_WhenMultipleColors_returnsThreeProducts() {
        Category category = saveCategory(null);

        saveProduct(TEST_PRICE, "Fehér/Törtfehér", category);
        saveProduct(TEST_PRICE, "Fekete", category);
        saveProduct(TEST_PRICE, "Menta/Kék", category);
        saveProduct(TEST_PRICE, "fehér/kék", category);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasColorIn(List.of("fehér", "kék")),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(3, products.size(), "Expected size 3, but actual size is " + products.size());
    }

    // Category Filter
    @Test
    public void productRepository_findAllWithCategoryId_returnsThreeProducts() {
        Category category1 = saveCategory(null);
        Category category2 = saveCategory(null);

        saveMultipleProducts(category1, 3);
        saveProduct(TEST_PRICE, TEST, category2);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasCategoryId(category1.getId()),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(3, products.size(), "Expected size 3, but actual size is " + products.size());
    }

    @Test
    public void productRepository_findAllWithRootCategoryName_returnsFourProducts() {
        Category rootCategory = saveCategory(null);
        Category subCategory = saveCategory(rootCategory);
        Category subSubCategory = saveCategory(subCategory);

        saveMultipleProducts(subSubCategory, 4);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasRootCategoryName(rootCategory.getName()),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(4, products.size(), "Expected size 4, but actual size is " + products.size());
    }

    // Size Filter
    @Test
    public void productRepository_findAllWithAllSizes_returnsFourProducts() {
        Category category = saveCategory(null);

        saveMultipleProducts(category, 4);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasSize("all"),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(4, products.size(), "Expected size 4, but actual size is " + products.size());
    }

    @Test
    public void productRepository_findAllWithSizeM_returnsOneProduct() {
        Category category = saveCategory(null);

        List<ProductStock> stock1 = List.of(
                createStock("XS", 1),
                createStock("M", 2),
                createStock("L", 12)
        );

        List<ProductStock> stock2 = List.of(
                createStock("XS", 1),
                createStock("L", 12)
        );

        saveProductWithStock(category, stock1);
        saveProductWithStock(category, stock2);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasSize("M"),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(1, products.size(), "Expected size 1, but actual size is " + products.size());
    }

    @Test
    public void productRepository_findAllWithSizeL_returnsOneProduct() {
        Category category = saveCategory(null);

        List<ProductStock> stock1 = List.of(
                createStock("XS", 1),
                createStock("M", 2),
                createStock("L", 0)
        );

        List<ProductStock> stock2 = List.of(
                createStock("XS", 1),
                createStock("L", 12)
        );

        saveProductWithStock(category, stock1);
        saveProductWithStock(category, stock2);

        List<Product> products = productRepository.findAll(
                ProductSpecification.hasSize("L"),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(1, products.size(), "Expected size 1, but actual size is " + products.size());
    }

    // Price Range Filter
    @Test
    public void productRepository_findAllWithPriceBetween_returnsTwoProducts() {
        Category category = saveCategory(null);

        saveProduct(1000, TEST, category);
        saveProduct(15990, TEST, category);
        saveProduct(3990, TEST, category);
        saveProduct(90000, TEST, category);

        double minPrice = 3000;
        double maxPrice = 50000;

        List<Product> products = productRepository.findAll(
                ProductSpecification.priceBetween(minPrice, maxPrice),
                Sort.by(Sort.Order.asc("name"))
        );

        assertEquals(2, products.size(), "Expected size 2, but actual size is " + products.size());
    }

    private Category saveCategory(Category parent) {
        return categoryRepository.save(Category.builder()
                .name(ProductRepositoryTest.TEST)
                .parent(parent)
                .build());
    }

    private void saveMultipleProducts(Category category, int count) {
        for (int i = 0; i < count; i++) {
            saveProduct(ProductRepositoryTest.TEST_PRICE, ProductRepositoryTest.TEST, category);
        }
    }

    private void saveProduct(double price, String color, Category category) {
        productRepository.save(Product.builder()
                .name(ProductRepositoryTest.TEST)
                .color(color)
                .price(price)
                .description(TEST)
                .materials(TEST)
                .category(category)
                .build());
    }

    private void saveProductWithStock(Category category, List<ProductStock> stocks) {
        Product product = Product.builder()
                .name(ProductRepositoryTest.TEST)
                .color(ProductRepositoryTest.TEST)
                .price(ProductRepositoryTest.TEST_PRICE)
                .description(TEST)
                .materials(TEST)
                .category(category)
                .productStocks(stocks)
                .build();

        for (ProductStock stock : stocks) {
            stock.setProduct(product);
        }

        productRepository.save(product);
    }

    private ProductStock createStock(String size, long quantity) {
        return productStockRepository.save(
                ProductStock.builder()
                        .size(size)
                        .quantity(quantity)
                        .build()
        );
    }
}
