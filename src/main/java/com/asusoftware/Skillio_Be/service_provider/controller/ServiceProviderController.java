package com.asusoftware.Skillio_Be.service_provider.controller;

import com.asusoftware.Skillio_Be.service_provider.model.dto.CreateServiceProviderDto;
import com.asusoftware.Skillio_Be.service_provider.model.dto.ServiceProviderDto;
import com.asusoftware.Skillio_Be.service_provider.model.dto.UpdateProviderDto;
import com.asusoftware.Skillio_Be.service_provider.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService serviceProviderService;

    /**
     * Creează un provider nou.
     */
    @PostMapping
    public ResponseEntity<ServiceProviderDto> create(@RequestBody CreateServiceProviderDto dto) {
        ServiceProviderDto created = serviceProviderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Returnează detalii despre un provider.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceProviderDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceProviderService.getById(id));
    }

    /**
     * Actualizează informațiile unui provider.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceProviderDto> update(@PathVariable UUID id,
                                                     @RequestBody UpdateProviderDto dto) {
        return ResponseEntity.ok(serviceProviderService.update(id, dto));
    }

    /**
     * Returnează toți providerii (cu paginare, dacă vrei).
     */
    @GetMapping
    public ResponseEntity<List<ServiceProviderDto>> getAll() {
        return ResponseEntity.ok(serviceProviderService.getAll());
    }

    /**
     * (Opțional) Șterge un provider.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        serviceProviderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

