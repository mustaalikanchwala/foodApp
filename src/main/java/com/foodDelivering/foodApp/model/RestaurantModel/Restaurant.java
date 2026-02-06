package com.foodDelivering.foodApp.model.RestaurantModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 50)
    private String restaurantName;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(name = "fk_restaurant_user"))
    private Long ownerId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank
    private String address;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String state;

    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode")
    @Column(length = 6, nullable = false)
    private String pinCode;

    @Column(precision = 2, scale = 1, nullable = false)
    @DecimalMin(value = "1.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    private BigDecimal rating;

    private Integer totalRatings;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ApproveStatus approveStatus = ApproveStatus.PENDING;

    private LocalDateTime approvedAt;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @Column(precision = 9,scale = 6)
    private BigDecimal latitude;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @Column(precision = 9,scale = 6)
    private BigDecimal longitude;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime openingTime;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime closingTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
