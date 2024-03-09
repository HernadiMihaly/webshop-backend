package com.familywebshop.stylet.dto.order;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {

    private Long productId;

    private String size;

    private Integer quantity;

    private Double price;
}
