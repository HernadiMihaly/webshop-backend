package com.familywebshop.stylet.controller.order;

import com.familywebshop.stylet.model.order.DiscountCoupon;
import com.familywebshop.stylet.service.order.DiscountCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DiscountCouponController {

    private final DiscountCouponService discountCouponService;

    @GetMapping("/coupons")
    public ResponseEntity<?> getAllCoupons() {
        return ResponseEntity.ok(discountCouponService.getAllCoupons());
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<?> getCouponById(@PathVariable Long id) {
        return ResponseEntity.ok(discountCouponService.getCouponById(id));
    }

    @PostMapping("/coupons")
    public ResponseEntity<?> saveCoupon(@RequestBody DiscountCoupon coupon) {
        return ResponseEntity.ok(discountCouponService.saveCoupon(coupon));
    }

    @PostMapping("/coupons/{id}/activate")
    public ResponseEntity<?> activateCoupon(@PathVariable Long id) {
        return ResponseEntity.ok(discountCouponService.activateCoupon(id));
    }

    @PostMapping("/coupons/{id}/deactivate")
    public ResponseEntity<?> deactivateCoupon(@PathVariable Long id) {
        return ResponseEntity.ok(discountCouponService.deactivateCoupon(id));
    }

    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) {
        discountCouponService.deleteCoupon(id);

        return ResponseEntity.ok().build();
    }
}
