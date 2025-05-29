package com.asusoftware.Skillio_Be.subscription.controller;

import com.asusoftware.Skillio_Be.subscription.model.dto.SubscriptionDto;
import com.asusoftware.Skillio_Be.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/status/{providerId}")
    public ResponseEntity<String> getStatus(@PathVariable UUID providerId) {
        String status = subscriptionService.getCurrentStatusText(providerId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/all/{providerId}")
    public ResponseEntity<List<SubscriptionDto>> getAll(@PathVariable UUID providerId) {
        return ResponseEntity.ok(subscriptionService.getAllByProviderId(providerId));
    }

    @PostMapping("/cancel/{providerId}")
    public ResponseEntity<Void> cancel(@PathVariable UUID providerId) {
        subscriptionService.cancelSubscription(providerId);
        return ResponseEntity.noContent().build();
    }
}

