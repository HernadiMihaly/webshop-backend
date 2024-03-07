package com.familywebshop.stylet.repository.order;

import com.familywebshop.stylet.model.order.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {
}
