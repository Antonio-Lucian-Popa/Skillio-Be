package com.asusoftware.Skillio_Be.service_provider.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "service_providers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvider {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String category;

    private String description;

    @Column(nullable = false)
    private String location;

    private BigDecimal rating;

    @Column(name = "stripe_account_id")
    private String stripeAccountId;

    @Column(name = "is_active")
    private Boolean isActive;
}
