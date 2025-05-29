package com.asusoftware.Skillio_Be.subscription;

import com.asusoftware.Skillio_Be.service_provider.model.ServiceProvider;
import com.asusoftware.Skillio_Be.service_provider.repository.ServiceProviderRepository;
import com.asusoftware.Skillio_Be.subscription.model.Subscription;
import com.asusoftware.Skillio_Be.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionCleanupJob {

    private final SubscriptionRepository repository;
    private final ServiceProviderRepository providerRepository;

    @Scheduled(cron = "0 0 3 * * *") // zilnic la 3:00 AM
    public void disableExpiredProviders() {
        List<Subscription> expired = repository.findAll().stream()
                .filter(s -> ("trialing".equals(s.getStatus()) && s.getTrialEndDate() != null && s.getTrialEndDate().isBefore(LocalDateTime.now()))
                        || ("active".equals(s.getStatus()) && s.getEndDate() != null && s.getEndDate().isBefore(LocalDateTime.now())))
                .toList();

        Set<UUID> expiredProviderIds = expired.stream()
                .map(Subscription::getProviderId)
                .collect(Collectors.toSet());

        List<ServiceProvider> providers = providerRepository.findAllById(expiredProviderIds);
        providers.forEach(p -> p.setIsActive(false));

        providerRepository.saveAll(providers);
    }
}
