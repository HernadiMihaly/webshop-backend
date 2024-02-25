package com.familywebshop.stylet.dto.order;

import com.familywebshop.stylet.dto.product.ProductDto;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {

    private OrderDto orderDto;

    private ProductDto productDto;

    private Integer quantity;

    private Double price;
}
