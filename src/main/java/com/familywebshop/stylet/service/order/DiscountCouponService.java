package com.familywebshop.stylet.service.order;

import com.familywebshop.stylet.dto.order.CouponValidationRequestDto;
import com.familywebshop.stylet.model.order.DiscountCoupon;

import java.util.List;

public interface DiscountCouponService {

    List<DiscountCoupon> getAllCoupons();

    DiscountCoupon getCouponById(Long id);

    DiscountCoupon saveCoupon(DiscountCoupon coupon);

    void deleteCoupon(Long id);

    DiscountCoupon activateCoupon(Long id);

    DiscountCoupon deactivateCoupon(Long id);

    Double validateCoupon(CouponValidationRequestDto couponRequest);
}
