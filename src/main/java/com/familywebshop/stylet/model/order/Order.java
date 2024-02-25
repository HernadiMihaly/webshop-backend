package com.familywebshop.stylet.model.order;

import com.familywebshop.stylet.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Long totalPrice;

    private String discountCoupon;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String customerPhoneNumber;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    private String deliveryCompany;

    @Column(nullable = false)
    private String deliveryMethod;

    @Column(nullable = false)
    private String paymentMethod;

    private String trackingNumber;
}
