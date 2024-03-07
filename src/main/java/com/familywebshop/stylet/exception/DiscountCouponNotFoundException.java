package com.familywebshop.stylet.exception;

public class DiscountCouponNotFoundException extends RuntimeException {

    public DiscountCouponNotFoundException(String message) {
        super(message);
    }
    public DiscountCouponNotFoundException(Long id)  {
        super("Coupon with ID " + id  + " not found!");
    }
}
