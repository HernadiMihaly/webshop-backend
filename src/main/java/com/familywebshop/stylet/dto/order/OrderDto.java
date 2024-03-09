package com.familywebshop.stylet.dto.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private List<OrderItemDto> orderItemDtoList;

    private LocalDateTime orderDate;

    private String orderNumber;

    private String status;

    private Long totalPrice;

    private String discountCoupon;

    private String customerName;

    private String customerEmail;

    private String customerPhoneNumber;

    private String shippingAddress;

    private String deliveryCompany;

    private String deliveryMethod;

    private String paymentMethod;

    private String trackingNumber;
}
