package com.asusoftware.Skillio_Be.user.service;

import com.asusoftware.Skillio_Be.config.KeycloakService;
import com.asusoftware.Skillio_Be.exception.UserNotFoundException;
import com.asusoftware.Skillio_Be.service_provider.model.ServiceProvider;
import com.asusoftware.Skillio_Be.service_provider.repository.ServiceProviderRepository;
import com.asusoftware.Skillio_Be.subscription.model.Subscription;
import com.asusoftware.Skillio_Be.subscription.repository.SubscriptionRepository;
import com.asusoftware.Skillio_Be.subscription.service.SubscriptionService;
import com.asusoftware.Skillio_Be.user.model.Role;
import com.asusoftware.Skillio_Be.user.model.User;
import com.asusoftware.Skillio_Be.user.model.dto.LoginDto;
import com.asusoftware.Skillio_Be.user.model.dto.UserDto;
import com.asusoftware.Skillio_Be.user.model.dto.UserRegisterDto;
import com.asusoftware.Skillio_Be.user.model.dto.UserResponseDto;
import com.asusoftware.Skillio_Be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final KeycloakService keycloakService;
    private final SubscriptionService subscriptionService;
    private final ModelMapper mapper;

    /**
     * Înregistrare utilizator (client sau prestator).
     * Creează user în Keycloak și îl salvează local.
     */
    @Transactional
    public UserResponseDto register(UserRegisterDto dto) {
        String keycloakId = keycloakService.createKeycloakUser(dto);

        // 1. Salvăm userul local
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .keycloakId(UUID.fromString(keycloakId))
                .role(dto.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        // 2. Dacă e PROVIDER, activăm automat trial și service_provider
        if (dto.getRole() == Role.PROVIDER) {

            ServiceProvider provider = ServiceProvider.builder()
                    .userId(user.getId())
                    .category("NECLASIFICAT") // poate fi setat mai târziu din profil
                    .location("NECOMPLETAT")
                    .description("")
                    .rating(BigDecimal.ZERO)
                    .isActive(true) // activat prin trial
                    .build();

            serviceProviderRepository.save(provider);

            subscriptionService.createTrialSubscription(user.getId());
        }

        return mapper.map(user, UserResponseDto.class);
    }

    /**
     * Autentificare utilizator prin Keycloak (cu email + parolă).
     */
    public AccessTokenResponse login(LoginDto dto) {
        return keycloakService.loginUser(dto);
    }

    /**
     * Returnează utilizatorul local după keycloakId (autentificare).
     */
    public User getByKeycloakId(UUID keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException("Utilizatorul nu a fost găsit în baza locală."));
    }

    /**
     * Returnează utilizatorul local după ID (UUID din baza locală).
     */
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Userul nu a fost găsit"));
    }

    /**
     * Șterge utilizatorul local și din Keycloak (hard delete).
     */
    @Transactional
    public void deleteByKeycloakId(UUID keycloakId) {
        keycloakService.deleteKeycloakUser(keycloakId);
        userRepository.findByKeycloakId(keycloakId).ifPresent(userRepository::delete);
    }
}

