package com.asusoftware.Skillio_Be.service_provider.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateServiceProviderDto {

    @NotNull
    private UUID userId;

    @NotBlank
    private String category; // Ex: instalator, zugrav etc.

    @NotBlank
    private String description;

    @NotBlank
    private String location; // localitate sau zonă de lucru
}
