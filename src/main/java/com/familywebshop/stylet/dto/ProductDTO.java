package com.familywebshop.stylet.dto;

import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.ProductImage;
import com.familywebshop.stylet.model.ProductStock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;

    private String color;

    private Double price;

    private String description;

    private String materials;

    private Category category;

    private List<ProductStock> productStockList;

    private List<ProductImage> productImages;
}
