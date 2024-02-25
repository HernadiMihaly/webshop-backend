package com.familywebshop.stylet.controller.order;

import com.familywebshop.stylet.dto.order.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok().build();
    }
}
