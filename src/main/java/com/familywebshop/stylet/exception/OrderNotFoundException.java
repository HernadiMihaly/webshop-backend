package com.familywebshop.stylet.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String s) {
        super(s);
    }

    public OrderNotFoundException(Long id) {
        super("Order with ID " + id  + " not found!");
    }
}
