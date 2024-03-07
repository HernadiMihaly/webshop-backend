package com.familywebshop.stylet.service.order;

import com.familywebshop.stylet.model.order.DiscountCoupon;

import java.util.List;

public interface DiscountCouponService {

    List<DiscountCoupon> getAllCoupons();

    DiscountCoupon getCouponById(Long id);

    DiscountCoupon addCoupon(DiscountCoupon coupon);

    void deleteCoupon(Long id);

    void activateCoupon(Long id);

    void deactivateCoupon(Long id);
}
