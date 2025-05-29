package com.asusoftware.Skillio_Be.appointment.repository;

import com.asusoftware.Skillio_Be.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByClientId(UUID clientId);

    boolean existsByClientIdAndServiceIdAndStatus(UUID clientId, UUID serviceId, String status);

    List<Appointment> findByServiceId(UUID serviceId);
    @Query("""
    SELECT a FROM Appointment a
    JOIN Service s ON a.serviceId = s.id
    WHERE s.providerId = :providerId
""")
    List<Appointment> findByProviderId(@Param("providerId") UUID providerId);

    @Query("""
    SELECT COUNT(a) > 0 FROM Appointment a
    JOIN Service s ON a.serviceId = s.id
    WHERE s.providerId = :providerId
      AND a.appointmentTime < :endTime
      AND a.appointmentTime + a.duration > :startTime
      AND a.status IN ('PENDING', 'CONFIRMED')
""")
    boolean isProviderBusy(
            @Param("providerId") UUID providerId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );


    @Query("""
    SELECT COUNT(a) > 0 FROM Appointment a
    JOIN Service s ON a.serviceId = s.id
    WHERE a.clientId = :clientId
      AND s.providerId = :providerId
      AND a.status = 'COMPLETED'
""")
    boolean hasCompletedAppointment(@Param("clientId") UUID clientId,
                                    @Param("providerId") UUID providerId);


}
