package com.asusoftware.Skillio_Be.user.model.dto;

import com.asusoftware.Skillio_Be.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private UUID id;
    private UUID keycloakId;

    private String firstName;
    private String lastName;
    private String email;

    private Role role;
}