package com.asusoftware.Skillio_Be.subscription.controller;

import com.asusoftware.Skillio_Be.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/webhooks/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Void> handleStripeWebhook(@RequestBody Map<String, Object> payload) {
        Map<String, Object> data = (Map<String, Object>) payload.get("data");
        Map<String, Object> object = (Map<String, Object>) data.get("object");

        String stripeSubscriptionId = (String) object.get("id");
        String metadataProviderId = (String) ((Map<String, Object>) object.get("metadata")).get("providerId");

        UUID providerId = UUID.fromString(metadataProviderId);
        subscriptionService.createPaidSubscription(providerId, stripeSubscriptionId);

        return ResponseEntity.ok().build();
    }
}
