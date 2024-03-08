package com.familywebshop.stylet.service.order.impl;

import com.familywebshop.stylet.dto.order.CouponValidationRequestDto;
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
    public DiscountCoupon saveCoupon(DiscountCoupon coupon) {
        return discountCouponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long id) throws DiscountCouponNotFoundException {
        DiscountCoupon coupon = discountCouponRepository.findById(id)
                .orElseThrow(() -> new DiscountCouponNotFoundException(id));

        discountCouponRepository.deleteById(coupon.getId());
    }

    @Override
    public DiscountCoupon activateCoupon(Long id) throws DiscountCouponNotFoundException {
        DiscountCoupon coupon = discountCouponRepository.findById(id)
                .orElseThrow(() -> new DiscountCouponNotFoundException(id));

        coupon.setIsValid(true);

        discountCouponRepository.save(coupon);

        return coupon;
    }

    @Override
    public DiscountCoupon deactivateCoupon(Long id) throws DiscountCouponNotFoundException {
        DiscountCoupon coupon = discountCouponRepository.findById(id)
                .orElseThrow(() -> new DiscountCouponNotFoundException(id));

        coupon.setIsValid(false);

        discountCouponRepository.save(coupon);

        return coupon;
    }

    @Override
    public Double validateCoupon(CouponValidationRequestDto couponRequest) throws DiscountCouponNotFoundException{
       if (discountCouponRepository.findByName(couponRequest.getName()).isPresent() &&
               discountCouponRepository.findByName(couponRequest.getName()).get().getIsValid()){
           double percentage = (double) discountCouponRepository.findByName(couponRequest.getName()).get().getPercentage() /100;

           return (double) Math.round(couponRequest.getPrice() * (1-percentage));
       } else {
           throw new DiscountCouponNotFoundException("Discount coupon: " + couponRequest.getName() + " not found!");
       }
    }
}
