package com.asusoftware.Skillio_Be.service_provider.service;

import com.asusoftware.Skillio_Be.service_provider.model.ServiceProvider;
import com.asusoftware.Skillio_Be.service_provider.model.dto.CreateServiceProviderDto;
import com.asusoftware.Skillio_Be.service_provider.model.dto.ServiceProviderDto;
import com.asusoftware.Skillio_Be.service_provider.model.dto.UpdateProviderDto;
import com.asusoftware.Skillio_Be.service_provider.repository.ServiceProviderRepository;
import com.asusoftware.Skillio_Be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final ModelMapper mapper;

    /**
     * Creează un nou provider.
     */
    @Transactional
    public ServiceProviderDto create(CreateServiceProviderDto dto) {
        ServiceProvider provider = ServiceProvider.builder()
                .userId(dto.getUserId())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .rating(BigDecimal.ZERO) // rating inițial
                .build();

        return mapper.map(serviceProviderRepository.save(provider), ServiceProviderDto.class);
    }

    /**
     * Returnează un provider după ID.
     */
    public ServiceProviderDto getById(UUID id) {
        ServiceProvider provider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Providerul nu a fost găsit"));
        return mapper.map(provider, ServiceProviderDto.class);
    }

    /**
     * Actualizează informațiile unui provider.
     */
    @Transactional
    public ServiceProviderDto update(UUID id, UpdateProviderDto dto) {
        ServiceProvider provider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Providerul nu a fost găsit"));

        mapper.map(dto, provider); // actualizare cu ModelMapper
        return mapper.map(serviceProviderRepository.save(provider), ServiceProviderDto.class);
    }

    /**
     * Returnează toți providerii.
     */
    public List<ServiceProviderDto> getAll() {
        return serviceProviderRepository.findAll().stream()
                .map(provider -> mapper.map(provider, ServiceProviderDto.class))
                .toList();
    }

    /**
     * Șterge un provider după ID (opțional).
     */
    @Transactional
    public void delete(UUID id) {
        if (!serviceProviderRepository.existsById(id)) {
            throw new NotFoundException("Providerul nu există.");
        }
        serviceProviderRepository.deleteById(id);
    }
}


