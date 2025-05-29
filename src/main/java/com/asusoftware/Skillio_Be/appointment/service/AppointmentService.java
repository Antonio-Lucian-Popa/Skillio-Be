package com.asusoftware.Skillio_Be.appointment.service;

import com.asusoftware.Skillio_Be.appointment.model.Appointment;
import com.asusoftware.Skillio_Be.appointment.model.dto.AppointmentDto;
import com.asusoftware.Skillio_Be.appointment.model.dto.CreateAppointmentDto;
import com.asusoftware.Skillio_Be.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper mapper;

    public AppointmentDto create(CreateAppointmentDto dto) {
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
}
