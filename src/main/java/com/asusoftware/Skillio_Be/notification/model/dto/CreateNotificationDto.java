package com.asusoftware.Skillio_Be.notification.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNotificationDto {

    @NotNull
    private UUID userId;

    @NotBlank
    private String message;

    @NotBlank
    private String type; // SYSTEM, APPOINTMENT, REVIEW
}