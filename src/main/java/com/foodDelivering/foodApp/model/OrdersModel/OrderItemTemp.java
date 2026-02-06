package com.foodDelivering.foodApp.model.OrdersModel;

import com.foodDelivering.foodApp.model.FoodProductModel.Food;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item_temp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemTemp {

    /* =========================
       PRIMARY KEY
       ========================= */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* =========================
       FOREIGN KEYS
       ========================= */

    // orderTempId -> OrderTemp.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_temp_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_temp_order_temp")
    )
    private OrderTemp orderTemp;

    // foodId -> Food.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "food_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_temp_food")
    )
    private Food food;

    /* =========================
       ORDER DETAILS
       ========================= */

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer quantity;

    // Price at time of order (snapshot)
    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /* =========================
       OPTIONAL FIELDS
       ========================= */

    // "Extra spicy", "No onions"
    @Column(length = 255)
    private String specialRequest;

    /* =========================
       AUDIT FIELDS
       ========================= */

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
