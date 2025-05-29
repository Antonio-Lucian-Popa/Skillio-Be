package com.asusoftware.Skillio_Be.service.controller;

import com.asusoftware.Skillio_Be.service.model.dto.CreateServiceDto;
import com.asusoftware.Skillio_Be.service.model.dto.ServiceDto;
import com.asusoftware.Skillio_Be.service.model.dto.UpdateServiceDto;
import com.asusoftware.Skillio_Be.service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService; // nume evită conflictul cu clasa java.lang.Service

    /**
     * Creează un nou serviciu pentru un provider.
     */
    @PostMapping
    public ResponseEntity<ServiceDto> create(@RequestBody CreateServiceDto dto) {
        ServiceDto created = serviceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Returnează detalii despre un serviciu.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceService.getById(id));
    }

    /**
     * Returnează toate serviciile unui provider.
     */
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ServiceDto>> getByProvider(@PathVariable UUID providerId) {
        return ResponseEntity.ok(serviceService.getByProvider(providerId));
    }

    /**
     * Actualizează un serviciu.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto> update(@PathVariable UUID id, @RequestBody UpdateServiceDto dto) {
        return ResponseEntity.ok(serviceService.update(id, dto));
    }

    /**
     * Șterge un serviciu.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

