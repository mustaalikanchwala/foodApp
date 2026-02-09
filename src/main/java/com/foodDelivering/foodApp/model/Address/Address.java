package com.foodDelivering.foodApp.model.Address;

import com.foodDelivering.foodApp.model.UserModel.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

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
            foreignKey = @ForeignKey(name = "fk_address_user")
    )
    private User user;

    /* =========================
       ADDRESS FIELDS
       ========================= */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String label; // "HOME", "WORK", "OTHER"

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
    private String zipCode;

    /* =========================
       LOCATION (OPTIONAL)
       ========================= */
    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    /* =========================
       FLAGS
       ========================= */
    @NotNull
    @Column(nullable = false)
    private Boolean isDefault = false;

    /* =========================
       AUDIT FIELDS
       ========================= */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
