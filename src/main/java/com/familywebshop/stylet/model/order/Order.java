package com.familywebshop.stylet.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    private String orderNumber = "";

    @Column(nullable = false)
    private String status = "NEW";

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
