package com.familywebshop.stylet.dto.product;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDto {

    private Long id;

    private String size;

    private Long quantity;
}
