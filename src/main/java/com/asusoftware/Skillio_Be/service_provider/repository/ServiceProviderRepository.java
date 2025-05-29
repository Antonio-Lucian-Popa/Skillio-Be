package com.asusoftware.Skillio_Be.service_provider.repository;

import com.asusoftware.Skillio_Be.service_provider.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, UUID> {
    Optional<ServiceProvider> findByUserId(UUID userId);
}

