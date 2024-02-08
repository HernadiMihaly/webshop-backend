package com.familywebshop.stylet.util;

import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import com.familywebshop.stylet.model.ProductStock;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {
    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Product> hasRootCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> {

            Join<Product, Category> firstLevelJoin = root.join("category", JoinType.INNER);

            Join<Category, Category> secondLevelJoin = firstLevelJoin.join("parent", JoinType.INNER);

            Join<Category, Category> thirdLevelJoin = secondLevelJoin.join("parent", JoinType.INNER);

            return criteriaBuilder.equal(thirdLevelJoin.get("name"), categoryName);
        };
    }

    public static Specification<Product> hasSize(String size) {
        return (root, query, criteriaBuilder) -> {
            if ("all".equalsIgnoreCase(size)) {
                return criteriaBuilder.conjunction();
            }

            Join<Product, ProductStock> productStockJoin = root.join("productStocks", JoinType.INNER);

            Predicate sizePredicate = criteriaBuilder.equal(productStockJoin.get("size"), size);

            Predicate quantityPredicate = criteriaBuilder.greaterThan(productStockJoin.get("quantity"), 0);

            return criteriaBuilder.and(sizePredicate, quantityPredicate);
        };
    }

    public static Specification<Product> hasColorIn(List<String> colors) {
        return (root, query, criteriaBuilder) -> {
            if (colors.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                List<String> lowercaseColors = colors.stream()
                        .map(String::toLowerCase)
                        .toList();

                Predicate[] predicates = lowercaseColors.stream()
                        .map(color -> criteriaBuilder.like(criteriaBuilder.lower(root.get("color")), "%" + color + "%"))
                        .toArray(Predicate[]::new);

                return criteriaBuilder.or(predicates);
            }
        };
    }

    public static Specification<Product> priceBetween(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }
}
