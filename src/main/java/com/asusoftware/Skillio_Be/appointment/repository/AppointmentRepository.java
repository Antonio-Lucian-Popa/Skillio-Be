package com.asusoftware.Skillio_Be.appointment.repository;

import com.asusoftware.Skillio_Be.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByClientId(UUID clientId);

    boolean existsByClientIdAndServiceIdAndStatus(UUID clientId, UUID serviceId, String status);

    List<Appointment> findByServiceId(UUID serviceId);
}
