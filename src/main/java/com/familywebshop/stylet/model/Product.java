package com.familywebshop.stylet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String color;
    private Double price;
    private String description;
    private String materials;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "stock_order")
    private List<ProductStock> productStockList;

    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL ,orphanRemoval = true)
    @OrderColumn(name = "product_order")
    private List<ProductImage> productImages;

}
