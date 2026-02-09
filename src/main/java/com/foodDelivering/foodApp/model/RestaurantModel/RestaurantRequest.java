package com.foodDelivering.foodApp.model.RestaurantModel;

import com.foodDelivering.foodApp.model.UserModel.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "restaurant_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequest {

    /* =========================
       PRIMARY KEY
       ========================= */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* =========================
       FOREIGN KEY
       ========================= */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_restaurant_request_user")
    )
    private User user;

    /* =========================
       RESTAURANT DETAILS
       ========================= */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String street;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String city;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String state;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String pinCode;

    @NotBlank
    @Email
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 15)
    private String phone;

    /* =========================
       LOCATION
       ========================= */
    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    /* =========================
       DOCUMENTS (JSON)
       ========================= */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private Map<String, String> documents; // {"fssai": "url1", "license": "url2"}

    /* =========================
       STATUS WORKFLOW
       ========================= */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Column(length = 500)
    private String rejectionReason;

    /* =========================
       ADMIN APPROVAL
       ========================= */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by", foreignKey = @ForeignKey(name = "fk_restaurant_request_admin"))
    private User approvedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
