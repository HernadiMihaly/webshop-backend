package com.familywebshop.stylet.model;

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

    private String orderNumber;

    private String status;

    private Long totalPrice;

    private String discountCoupon;

    private String customerName;

    private String customerEmail;

    private String customerPhoneNumber;

    private String shippingAddress;

    private String billingAddress;

    private String deliveryCompany;

    private String deliveryMethod;

    private String paymentMethod;

    private String trackingNumber;
}
