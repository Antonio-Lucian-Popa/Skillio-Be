package com.asusoftware.Skillio_Be.service_provider.model.dto;

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
public class ServiceProviderDto {
    private UUID id;
    private UUID userId;
    private String category;
    private String description;
    private String location;
    private BigDecimal rating;
    private Boolean isActive;
}

