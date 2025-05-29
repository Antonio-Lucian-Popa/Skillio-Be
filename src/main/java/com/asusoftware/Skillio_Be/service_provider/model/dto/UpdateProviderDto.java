package com.asusoftware.Skillio_Be.service_provider.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProviderDto {
    private String category;
    private String description;
    private String location;
}
