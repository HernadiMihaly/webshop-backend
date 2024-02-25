package com.familywebshop.stylet.service.order.impl;

import com.familywebshop.stylet.dto.order.OrderDto;
import com.familywebshop.stylet.model.order.Order;
import com.familywebshop.stylet.repository.order.OrderItemRepository;
import com.familywebshop.stylet.repository.order.OrderRepository;
import com.familywebshop.stylet.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    @Override
    public void saveOrder(OrderDto orderDto) {
        OrderDto filledOrderDto = this.generateOrderDetails(orderDto);

        //TODO
    }

    private OrderDto generateOrderDetails(OrderDto orderDto){
        return null; //TODO
    }

    private Order mapDtoToEntity(OrderDto orderDto){
        return null; //TODO
    }
}
