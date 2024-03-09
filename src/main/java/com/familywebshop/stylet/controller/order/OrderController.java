package com.familywebshop.stylet.controller.order;

import com.familywebshop.stylet.dto.order.OrderDto;
import com.familywebshop.stylet.dto.order.OrderRequestDto;
import com.familywebshop.stylet.exception.OrderNotFoundException;
import com.familywebshop.stylet.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody OrderDto orderDto){
        try {
            orderService.saveOrder(orderDto);
        } catch (OrderNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Order completed!");
    }
}
