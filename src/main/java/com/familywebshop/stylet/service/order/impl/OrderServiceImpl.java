package com.familywebshop.stylet.service.order.impl;

import com.familywebshop.stylet.dto.order.OrderRequestDto;
import com.familywebshop.stylet.exception.OrderNotFoundException;
import com.familywebshop.stylet.model.order.Order;
import com.familywebshop.stylet.model.order.OrderItem;
import com.familywebshop.stylet.repository.order.OrderItemRepository;
import com.familywebshop.stylet.repository.order.OrderRepository;
import com.familywebshop.stylet.service.order.OrderService;
import com.familywebshop.stylet.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductService productService;

    @Override
    @Transactional
    public void saveOrder(OrderRequestDto orderRequestDto) throws OrderNotFoundException {
        //Save order with generated order number
        Order order = orderRepository.save(orderRequestDto.getOrder());

        order.setOrderNumber(generateOrderNumber(order.getId()));

        orderRepository.save(order);

        //Save order details (items) & reduce quantity of ordered items
        List<OrderItem> orderItemList = orderRequestDto.getOrderItemList();

        for (OrderItem orderItem : orderItemList){
            orderItem.setOrder(order);

            orderItemRepository.save(orderItem);
            productService.reduceQuantity(orderItem.getProduct(), orderItem.getSize(), orderItem.getQuantity());
        }
    }

    private String generateOrderNumber(Long orderId){
        StringBuilder orderNumberBuilder = new StringBuilder("FN");
        int randomNumber = 10000 + new Random().nextInt(90000);

        orderNumberBuilder.append(randomNumber);
        orderNumberBuilder.append(orderId);

        return orderNumberBuilder.toString();
    }
}
