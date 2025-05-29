package com.asusoftware.Skillio_Be.service.repository;

import com.asusoftware.Skillio_Be.service.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    List<ServiceEntity> findByProviderId(UUID providerId);
    @Query("SELECT s.id FROM Service s WHERE s.providerId = :providerId")
    List<UUID> findIdsByProviderId(@Param("providerId") UUID providerId);

}
