package com.familywebshop.stylet.dto;

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
