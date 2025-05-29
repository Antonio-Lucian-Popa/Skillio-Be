package com.asusoftware.Skillio_Be.appointment.controller;

import com.asusoftware.Skillio_Be.appointment.model.dto.AppointmentDto;
import com.asusoftware.Skillio_Be.appointment.model.dto.CreateAppointmentDto;
import com.asusoftware.Skillio_Be.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Creează o programare nouă.
     */
    @PostMapping
    public ResponseEntity<AppointmentDto> create(@RequestBody CreateAppointmentDto dto) {
        AppointmentDto created = appointmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Returnează toate programările unui client.
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AppointmentDto>> getByClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(appointmentService.getByClient(clientId));
    }

    /**
     * Returnează toate programările unui serviciu.
     */
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<AppointmentDto>> getByService(@PathVariable UUID serviceId) {
        return ResponseEntity.ok(appointmentService.getByService(serviceId));
    }

    /**
     * Actualizează statusul unei programări (ex: CONFIRMED, COMPLETED, CANCELED).
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        appointmentService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}

