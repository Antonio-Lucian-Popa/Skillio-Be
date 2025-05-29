package com.asusoftware.Skillio_Be.appointment.service;

import com.asusoftware.Skillio_Be.appointment.model.Appointment;
import com.asusoftware.Skillio_Be.appointment.model.dto.AppointmentDto;
import com.asusoftware.Skillio_Be.appointment.model.dto.CreateAppointmentDto;
import com.asusoftware.Skillio_Be.appointment.repository.AppointmentRepository;
import com.asusoftware.Skillio_Be.exception.ConflictException;
import com.asusoftware.Skillio_Be.service.model.ServiceEntity;
import com.asusoftware.Skillio_Be.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final ModelMapper mapper;

    public AppointmentDto create(CreateAppointmentDto dto) {
        ServiceEntity service = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new NotFoundException("Serviciul nu a fost găsit"));
        UUID providerId = service.getProviderId();

        LocalDateTime start = dto.getAppointmentTime();
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        boolean busy = appointmentRepository.isProviderBusy(providerId, start, end);
        if (busy) {
            throw new ConflictException("Prestatorul este ocupat în acest interval.");
        }

        Appointment appointment = Appointment.builder()
                .serviceId(dto.getServiceId())
                .clientId(dto.getClientId())
                .appointmentTime(dto.getAppointmentTime())
                .address(dto.getAddress())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.map(appointmentRepository.save(appointment), AppointmentDto.class);
    }

    public void updateStatus(UUID id, String newStatus) {
        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programare inexistentă"));
        a.setStatus(newStatus);
        appointmentRepository.save(a);
    }

    public List<AppointmentDto> getByClient(UUID clientId) {
        return appointmentRepository.findByClientId(clientId)
                .stream()
                .map(appointment -> mapper.map(appointment, AppointmentDto.class))
                .toList();
    }

    public List<AppointmentDto> getByService(UUID serviceId) {
        List<Appointment> appointments = appointmentRepository.findByServiceId(serviceId);
        return appointments.stream()
                .map(appointment -> mapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getByProvider(UUID providerId) {
        List<Appointment> appointments = appointmentRepository.findByProviderId(providerId);
        return appointments.stream()
                .map(appointment -> mapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }


}
