package com.asusoftware.Skillio_Be.subscription.repository;

import com.asusoftware.Skillio_Be.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findTopByProviderIdOrderByTrialEndDateDesc(UUID providerId);
    List<Subscription> findAllByProviderIdOrderByTrialEndDateDesc(UUID providerId);

    @Query("SELECT s FROM Subscription s " +
            "WHERE s.status = 'trialing' AND s.trialEndDate BETWEEN :now AND :limit")
    List<Subscription> findTrialExpiringIn(@Param("now") LocalDateTime now,
                                           @Param("limit") LocalDateTime limit);

    // Shortcut convenience method
    default List<Subscription> findTrialExpiringIn(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plusDays(days);
        return findTrialExpiringIn(now, limit);
    }
}

