package com.familywebshop.stylet.dto;

import lombok.*;

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

    private List<ProductStockDto> productStock;

    private List<ProductPhotoDto> productPhotos;

}
