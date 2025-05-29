package com.asusoftware.Skillio_Be.service_provider.service;

import com.asusoftware.Skillio_Be.service_provider.model.ServiceProvider;
import com.asusoftware.Skillio_Be.service_provider.model.dto.ServiceProviderDto;
import com.asusoftware.Skillio_Be.service_provider.model.dto.UpdateProviderDto;
import com.asusoftware.Skillio_Be.service_provider.repository.ServiceProviderRepository;
import com.asusoftware.Skillio_Be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository providerRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public ServiceProviderDto getByUserId(UUID userId) {
        return providerRepository.findByUserId(userId)
                .map(provider -> mapper.map(provider, ServiceProviderDto.class))
                .orElseThrow(() -> new RuntimeException("Prestatorul nu a fost găsit"));
    }

    public void updateProfile(UUID providerId, UpdateProviderDto dto) {
        ServiceProvider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestator inexistent"));

        provider.setCategory(dto.getCategory());
        provider.setLocation(dto.getLocation());
        provider.setDescription(dto.getDescription());

        providerRepository.save(provider);
    }
}

