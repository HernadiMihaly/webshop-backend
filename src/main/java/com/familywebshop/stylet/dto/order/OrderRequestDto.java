package com.familywebshop.stylet.dto.order;

import com.familywebshop.stylet.model.order.Order;
import com.familywebshop.stylet.model.order.OrderItem;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    private Order order;

    private List<OrderItem> orderItemList;
}
