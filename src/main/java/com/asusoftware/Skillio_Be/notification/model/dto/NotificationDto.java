package com.asusoftware.Skillio_Be.notification.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private UUID id;
    private UUID userId;
    private String message;
    private String type; // SYSTEM, APPOINTMENT, REVIEW
    private Boolean isRead;
    private LocalDateTime createdAt;
}
