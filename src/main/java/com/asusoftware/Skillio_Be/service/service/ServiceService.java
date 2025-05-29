package com.asusoftware.Skillio_Be.service.service;

import com.asusoftware.Skillio_Be.service.model.ServiceEntity;
import com.asusoftware.Skillio_Be.service.model.dto.CreateServiceDto;
import com.asusoftware.Skillio_Be.service.model.dto.ServiceDto;
import com.asusoftware.Skillio_Be.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ModelMapper mapper;

    public List<ServiceDto> getServicesByProvider(UUID providerId) {
        return serviceRepository.findByProviderId(providerId)
                .stream()
                .map(service -> mapper.map(service, ServiceDto.class))
                .toList();
    }

    public ServiceDto create(CreateServiceDto dto) {
        ServiceEntity service = ServiceEntity.builder()
                .id(UUID.randomUUID())
                .providerId(dto.getProviderId())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .durationMinutes(dto.getDurationMinutes())
                .description(dto.getDescription())
                .paymentMethod(dto.getPaymentMethod())
                .build();

        return mapper.map(serviceRepository.save(service), ServiceDto.class);
    }
}
