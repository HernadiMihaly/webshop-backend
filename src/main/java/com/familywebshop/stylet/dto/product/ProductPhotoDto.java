package com.familywebshop.stylet.dto.product;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPhotoDto {

    private Long id;

    private String imageUrl;
}
