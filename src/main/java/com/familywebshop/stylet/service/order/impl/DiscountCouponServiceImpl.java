package com.familywebshop.stylet.service.order.impl;

import com.familywebshop.stylet.exception.DiscountCouponNotFoundException;
import com.familywebshop.stylet.model.order.DiscountCoupon;
import com.familywebshop.stylet.repository.order.DiscountCouponRepository;
import com.familywebshop.stylet.service.order.DiscountCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiscountCouponServiceImpl implements DiscountCouponService {

    private final DiscountCouponRepository discountCouponRepository;

    @Override
    public List<DiscountCoupon> getAllCoupons() {
        return discountCouponRepository.findAll();
    }

    @Override
    public DiscountCoupon getCouponById(Long id) throws DiscountCouponNotFoundException{
        return discountCouponRepository.findById(id)
                .orElseThrow(() -> new DiscountCouponNotFoundException(id));
    }

    @Override
    public DiscountCoupon addCoupon(DiscountCoupon coupon) {
        return discountCouponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        discountCouponRepository.deleteById(id);
    }

    @Override
    public void activateCoupon(Long id) {
        DiscountCoupon coupon = discountCouponRepository.findById(id)
                .orElseThrow(() -> new DiscountCouponNotFoundException(id));

        coupon.setIsValid(true);

        discountCouponRepository.save(coupon);
    }

    @Override
    public void deactivateCoupon(Long id) {
        DiscountCoupon coupon = discountCouponRepository.findById(id)
                .orElseThrow(() -> new DiscountCouponNotFoundException(id));

        coupon.setIsValid(false);

        discountCouponRepository.save(coupon);
    }
}
