package com.familywebshop.stylet.service.order;

import com.familywebshop.stylet.dto.order.OrderDto;
import com.familywebshop.stylet.dto.order.OrderRequestDto;
import com.familywebshop.stylet.exception.OrderNotFoundException;

public interface OrderService {

    void saveOrder(OrderDto orderDto) throws OrderNotFoundException;
}
