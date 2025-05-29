package com.asusoftware.Skillio_Be.subscription.repository;

import com.asusoftware.Skillio_Be.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findTopByProviderIdOrderByTrialEndDateDesc(UUID providerId);
    List<Subscription> findAllByProviderIdOrderByTrialEndDateDesc(UUID providerId);
}

