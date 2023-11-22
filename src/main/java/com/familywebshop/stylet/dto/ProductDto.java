package com.familywebshop.stylet.dto;

import com.familywebshop.stylet.model.Category;
import lombok.*;

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

    private Category category;

    private String imageUrl;

}
