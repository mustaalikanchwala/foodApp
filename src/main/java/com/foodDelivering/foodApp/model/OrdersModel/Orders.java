package com.foodDelivering.foodApp.model.OrdersModel;

import com.foodDelivering.foodApp.model.Address.Address;
import com.foodDelivering.foodApp.model.RestaurantModel.Restaurant;
import com.foodDelivering.foodApp.model.UserModel.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
    @Table(name = "orders")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Orders {

        /* =========================
           PRIMARY KEY
           ========================= */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    /* =========================
       FOREIGN KEYS
       ========================= */

        // OrderTempId
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(
                name = "order_temp_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_order_order_temp")
        )
        private OrderTemp orderTemp;

        // userId -> User.id
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(
                name = "user_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_order_user")
        )
        private User user;

        // restaurantId -> Restaurant.id
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(
                name = "restaurant_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_order_restaurant")
        )
        private Restaurant restaurant;

        // deliveryAddressId -> Address.id (better approach)
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(
                name = "delivery_address_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_order_address")
        )
        private Address deliveryAddress;

    /* =========================
       AMOUNTS
       ========================= */

        @NotNull
        @DecimalMin("0.0")
        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal totalAmount;

        @NotNull
        @DecimalMin("0.0")
        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal deliveryCharges;

        @NotNull
        @DecimalMin("0.0")
        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal taxAmount;

        // total + delivery + tax
        @NotNull
        @DecimalMin("0.0")
        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal finalAmount;

    /* =========================
       ENUMS
       ========================= */

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 30)
        @Builder.Default
        private OrderStatus status = OrderStatus.PLACED;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 10)
        private PaymentMethod paymentMethod;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        @Builder.Default
        private PaymentStatus paymentStatus = PaymentStatus.PAYMENT_PENDING;

    /* =========================
       TIME FIELDS
       ========================= */

        @Column(nullable = false)
        private LocalDateTime orderDate;

        private LocalDateTime estimatedDeliveryTime;

        @Column(nullable = true)
        private LocalDateTime actualDeliveryTime;

    /* =========================
       OPTIONAL FIELDS
       ========================= */

        @Column(columnDefinition = "TEXT")
        private String specialInstructions;


    /* =========================
       AUDIT FIELDS
       ========================= */

        @CreationTimestamp
        @Column(updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
    }


