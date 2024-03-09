package com.familywebshop.stylet.service.order.impl;

import com.familywebshop.stylet.dto.order.OrderDto;
import com.familywebshop.stylet.dto.order.OrderItemDto;
import com.familywebshop.stylet.dto.order.OrderRequestDto;
import com.familywebshop.stylet.exception.OrderNotFoundException;
import com.familywebshop.stylet.exception.ProductNotFoundException;
import com.familywebshop.stylet.model.order.Order;
import com.familywebshop.stylet.model.order.OrderItem;
import com.familywebshop.stylet.repository.order.OrderItemRepository;
import com.familywebshop.stylet.repository.order.OrderRepository;
import com.familywebshop.stylet.service.order.OrderService;
import com.familywebshop.stylet.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
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
    public void saveOrder(OrderDto orderDto) throws OrderNotFoundException {
        //Save order with generated order number
        Order order = orderRepository.save(mapOrderDtoToEntity(orderDto));
        order.setOrderNumber(generateOrderNumber(order.getId()));
        Order orderWithOrderNumber = orderRepository.save(order);

        saveOrderItems(orderDto.getOrderItemDtoList(), orderWithOrderNumber);
    }

    private Order mapOrderDtoToEntity(OrderDto orderDto){
        return Order.builder()
                .orderItemList(Collections.emptyList())
                .orderDate(LocalDateTime.now())
                .orderNumber("In progress")
                .status("NEW")
                .totalPrice(orderDto.getTotalPrice())
                .discountCoupon(orderDto.getDiscountCoupon())
                .customerName(orderDto.getCustomerName())
                .customerEmail(orderDto.getCustomerEmail())
                .customerPhoneNumber(orderDto.getCustomerPhoneNumber())
                .shippingAddress(orderDto.getShippingAddress())
                .deliveryCompany(orderDto.getDeliveryCompany())
                .deliveryMethod(orderDto.getDeliveryMethod())
                .paymentMethod(orderDto.getPaymentMethod())
                .trackingNumber("In progress")
                .build();
    }
    private void saveOrderItems(List<OrderItemDto> orderItemDtoList, Order order) {
        List<OrderItem> orderItemList = orderItemDtoList.stream()
                .map(orderItemDto -> mapOrderItemDtoToEntity(orderItemDto, order))
                .toList();

        orderItemList.forEach(orderItem -> {
            orderItemRepository.save(orderItem);
            productService.reduceQuantity(orderItem.getProduct(), orderItem.getSize(), orderItem.getQuantity());
        });
    }

    private OrderItem mapOrderItemDtoToEntity(OrderItemDto orderItemDto, Order order) throws ProductNotFoundException {
        return OrderItem.builder()
                .order(order)
                .product(productService.findProductById(orderItemDto.getProductId()))
                .size(orderItemDto.getSize())
                .quantity(orderItemDto.getQuantity())
                .price(orderItemDto.getPrice())
                .build();
    }

    private String generateOrderNumber(Long orderId){
        StringBuilder orderNumberBuilder = new StringBuilder("FN");
        int randomNumber = 10000 + new Random().nextInt(90000);

        orderNumberBuilder.append(randomNumber);
        orderNumberBuilder.append(orderId);

        return orderNumberBuilder.toString();
    }
}
