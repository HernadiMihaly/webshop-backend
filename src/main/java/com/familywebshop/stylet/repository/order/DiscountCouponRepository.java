package com.familywebshop.stylet.repository.order;

import com.familywebshop.stylet.model.order.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {

    Optional<DiscountCoupon> findByName(String name);
}
