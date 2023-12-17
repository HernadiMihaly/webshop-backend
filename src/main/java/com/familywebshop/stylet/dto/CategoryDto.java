package com.familywebshop.stylet.dto;

import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;

    private String name;

    private Long parentId;

    private List<CategoryDto> subCategories;

}
