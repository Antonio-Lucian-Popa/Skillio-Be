package com.asusoftware.Skillio_Be.appointment.model.dto;

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
public class AppointmentDto {
    private UUID id;
    private UUID serviceId;
    private UUID clientId;
    private LocalDateTime appointmentTime;
    private String address;
    private String status;
    private LocalDateTime createdAt;
}
