package com.asusoftware.Skillio_Be.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceDto {
    private UUID providerId;
    private String title;
    private BigDecimal price;
    private Integer durationMinutes;
    private String description;
    private String paymentMethod; // ex: cash, card, revolut
}
