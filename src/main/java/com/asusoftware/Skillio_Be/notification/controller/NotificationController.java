package com.asusoftware.Skillio_Be.notification.controller;

import com.asusoftware.Skillio_Be.notification.model.dto.CreateNotificationDto;
import com.asusoftware.Skillio_Be.notification.model.dto.NotificationDto;
import com.asusoftware.Skillio_Be.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllForCurrentUser(@AuthenticationPrincipal Jwt principal) {
        UUID userId = UUID.fromString(principal.getSubject());
        return ResponseEntity.ok(notificationService.getAllForUser(userId));
    }

    @PostMapping
    public ResponseEntity<NotificationDto> create(@RequestBody @Valid CreateNotificationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(dto));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

