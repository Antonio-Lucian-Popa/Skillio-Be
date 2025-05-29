package com.asusoftware.Skillio_Be.review.repository;

import com.asusoftware.Skillio_Be.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    boolean existsByClientIdAndServiceId(UUID clientId, UUID serviceId);

    @Query("""
    SELECT COUNT(a) > 0 FROM Appointment a
    JOIN Service s ON a.serviceId = s.id
    WHERE a.clientId = :clientId
      AND s.providerId = :providerId
      AND a.status = 'COMPLETED'
""")
    boolean hasCompletedAppointment(@Param("clientId") UUID clientId,
                                    @Param("providerId") UUID providerId);

    List<Review> findByServiceIdIn(List<UUID> serviceIds);
}
