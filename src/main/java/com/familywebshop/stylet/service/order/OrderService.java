package com.familywebshop.stylet.service.order;

import com.familywebshop.stylet.dto.order.OrderRequestDto;
import com.familywebshop.stylet.exception.OrderNotFoundException;

public interface OrderService {

    void saveOrder(OrderRequestDto orderRequestDto) throws OrderNotFoundException;
}
