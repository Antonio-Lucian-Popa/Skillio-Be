package com.asusoftware.Skillio_Be.user.controller;

import com.asusoftware.Skillio_Be.user.model.dto.LoginDto;
import com.asusoftware.Skillio_Be.user.model.dto.UserRegisterDto;
import com.asusoftware.Skillio_Be.user.model.dto.UserResponseDto;
import com.asusoftware.Skillio_Be.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    /**
     * Înregistrare cont nou.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRegisterDto dto) {
        UserResponseDto response = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login utilizator (returnează token).
     */
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    /**
     * Returnează detalii user după ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.map(userService.getById(id), UserResponseDto.class));
    }

    /**
     * Șterge un user după ID (și din Keycloak).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UUID id) {
        userService.deleteByKeycloakId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returnează userul logat pe baza keycloakId din token.
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal Jwt principal) {
        UUID keycloakId = UUID.fromString(principal.getSubject());
        return ResponseEntity.ok(mapper.map(userService.getByKeycloakId(keycloakId), UserResponseDto.class));
    }
}

