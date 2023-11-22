package com.familywebshop.stylet.dto;

import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;

    private String name;

    private Category parentCategory;

}
