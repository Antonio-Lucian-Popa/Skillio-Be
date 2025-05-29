package com.asusoftware.Skillio_Be.subscription.service;

import com.asusoftware.Skillio_Be.subscription.model.Subscription;
import com.asusoftware.Skillio_Be.subscription.model.dto.SubscriptionDto;
import com.asusoftware.Skillio_Be.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository repository;
    private final ModelMapper mapper;

    /**
     * Creează un abonament de tip trial pentru un prestator nou înregistrat.
     */
    public Subscription createTrialSubscription(UUID providerId) {
        Subscription subscription = Subscription.builder()
                .providerId(providerId)
                .stripeSubscriptionId("trial-no-stripe")
                .status("trialing")
                .trialStartDate(LocalDateTime.now())
                .trialEndDate(LocalDateTime.now().plusDays(7))
                .build();

        return repository.save(subscription);
    }

    /**
     * Creează un abonament plătit după confirmarea Stripe.
     */
    public Subscription createPaidSubscription(UUID providerId, String stripeSubscriptionId) {
        Subscription subscription = Subscription.builder()
                .providerId(providerId)
                .stripeSubscriptionId(stripeSubscriptionId)
                .status("active")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1)) // sau din webhook
                .build();

        return repository.save(subscription);
    }

    /**
     * Verifică dacă prestatorul are un abonament activ sau trial valid.
     */
    public boolean isSubscriptionActive(UUID providerId) {
        Subscription s = repository.findTopByProviderIdOrderByTrialEndDateDesc(providerId)
                .orElse(null);

        if (s == null) return false;
        if ("active".equals(s.getStatus())) return true;
        if ("trialing".equals(s.getStatus()) && s.getTrialEndDate().isAfter(LocalDateTime.now()))
            return true;

        return false;
    }

    /**
     * Returnează statusul curent al abonamentului.
     */
    public String getCurrentStatusText(UUID providerId) {
        Subscription s = repository.findTopByProviderIdOrderByTrialEndDateDesc(providerId)
                .orElse(null);

        if (s == null) return "no_subscription";
        if ("active".equals(s.getStatus())) return "active";
        if ("trialing".equals(s.getStatus()) && s.getTrialEndDate().isAfter(LocalDateTime.now()))
            return "trialing";
        return "expired";
    }

    /**
     * Anulează ultimul abonament al prestatorului.
     */
    public void cancelSubscription(UUID providerId) {
        Subscription last = repository.findTopByProviderIdOrderByTrialEndDateDesc(providerId)
                .orElseThrow(() -> new RuntimeException("Abonament inexistent"));

        last.setStatus("canceled");
        repository.save(last);
    }

    /**
     * Returnează toate abonamentele unui prestator.
     */
    public List<SubscriptionDto> getAllByProviderId(UUID providerId) {
        return repository.findAllByProviderIdOrderByTrialEndDateDesc(providerId)
                .stream()
                .map(subscription -> mapper.map(subscription, SubscriptionDto.class))
                .toList();
    }
}

