package com.asusoftware.Skillio_Be.subscription.controller;

import com.asusoftware.Skillio_Be.subscription.service.StripeCheckoutService;
import com.asusoftware.Skillio_Be.user.model.User;
import com.asusoftware.Skillio_Be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final StripeCheckoutService stripeCheckoutService;
    private final UserService userService;

    @PostMapping("/start")
    public ResponseEntity<String> startCheckout(@RequestParam UUID providerId) {
        // ia userul providerului pentru a avea email
        User user = userService.getUserByProviderId(providerId);
        String url = stripeCheckoutService.createCheckoutSession(user.getEmail(), providerId);
        return ResponseEntity.ok(url);
    }
}

