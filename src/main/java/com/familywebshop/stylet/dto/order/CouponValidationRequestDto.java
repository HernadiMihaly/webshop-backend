package com.familywebshop.stylet.dto.order;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponValidationRequestDto {

    private String name;

    private Double price;
}
