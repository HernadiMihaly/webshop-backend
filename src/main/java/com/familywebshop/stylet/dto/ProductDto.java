package com.familywebshop.stylet.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String name;

    private String color;

    private Double price;

    private String description;

    private String materials;

    private Long category;

    private Date createdAt;

    private List<ProductPhotoDto> productPhotos;

    private List<ProductStockDto> productStocks;
}
