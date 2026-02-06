package com.foodDelivering.foodApp.model.FoodProductModel;

import com.foodDelivering.foodApp.model.FileCategory;
import com.foodDelivering.foodApp.model.RestaurantModel.Restaurant;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foodProducts")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id",nullable = false,foreignKey = @ForeignKey(name = "fk_restaurant_food"))
    private Restaurant restaurant;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(precision = 2, scale = 1, nullable = false)
    @DecimalMin(value = "1.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    private BigDecimal rating;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank
    private String imageKey;

    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    @NotNull
    @DecimalMin(value = "0.0" , inclusive = false)
    @Column(nullable = false,precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @DecimalMin(value = "0.0" , inclusive = false)
    @Column(precision = 10, scale = 2)
    private BigDecimal offerPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
