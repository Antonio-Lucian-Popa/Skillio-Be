package com.asusoftware.Skillio_Be.user.model.dto;

import com.asusoftware.Skillio_Be.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role; // ENUM: CLIENT, PROVIDER, ADMIN
}

