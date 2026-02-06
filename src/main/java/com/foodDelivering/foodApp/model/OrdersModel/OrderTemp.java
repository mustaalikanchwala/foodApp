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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_temp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTemp {

    /* =========================
       PRIMARY KEY
       ========================= */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* =========================
       FOREIGN KEYS
       ========================= */

    // userId -> User.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_temp_user")
    )
    private User user;

    // restaurantId -> Restaurant.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "restaurant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_temp_restaurant")
    )
    private Restaurant restaurant;

    // deliveryAddressId -> Address.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "delivery_address_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_temp_address")
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
       PAYMENT GATEWAY FIELDS
       ========================= */

    // Payment method (COD or ONLINE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PaymentMethod paymentMethod;

    // 1. Create Order response - frontend needs this
    @Column(unique = true, length = 100)
    private String razorpayOrderId;

    // 2. Webhook response - verify payment
    @Column(length = 100)
    private String razorpayPaymentId;

    // 3. Webhook security - prevent fraud
    @Column(length = 255)
    private String razorpaySignature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PAYMENT_PENDING;

    /* =========================
       OPTIONAL FIELDS
       ========================= */

    @Column(columnDefinition = "TEXT")
    private String specialInstructions;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isConfirmed = false;

    /* =========================
       TIME FIELDS
       ========================= */

   /* =========================
       TIME FIELDS
       ========================= */

    @Column(nullable = false)
    private LocalDateTime orderDate;

    private LocalDateTime estimatedDeliveryTime;

    @Column(nullable = true)
    private LocalDateTime actualDeliveryTime;

    /* =========================
       AUDIT FIELDS
       ========================= */

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Helper method to set expiry (30 minutes from creation)
    @PrePersist
    public void setExpiryTime() {
        if (this.orderDate == null) {
            this.orderDate = LocalDateTime.now();
        }
    }
}
