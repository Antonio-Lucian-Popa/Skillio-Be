package com.asusoftware.Skillio_Be.notification.service;

import com.asusoftware.Skillio_Be.notification.model.Notification;
import com.asusoftware.Skillio_Be.notification.model.dto.CreateNotificationDto;
import com.asusoftware.Skillio_Be.notification.model.dto.NotificationDto;
import com.asusoftware.Skillio_Be.notification.repository.NotificationRepository;
import com.asusoftware.Skillio_Be.subscription.model.Subscription;
import com.asusoftware.Skillio_Be.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebSocketNotificationService websocketService;
    private final ModelMapper mapper;

    public List<NotificationDto> getAllForUser(UUID userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notification -> mapper.map(notification, NotificationDto.class))
                .toList();
    }

    public NotificationDto create(CreateNotificationDto dto) {
        Notification notification = Notification.builder()
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .type(dto.getType())
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.map(repository.save(notification), NotificationDto.class);
    }

    public void markAsRead(UUID id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificarea nu a fost găsită"));
        notification.setIsRead(true);
        repository.save(notification);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Scheduled(cron = "0 0 8 * * *") // zilnic la 8 dimineața
    public void notifyExpiringTrials() {
        List<Subscription> subs = subscriptionRepository.findTrialExpiringIn(2); // expiră în 2 zile

        for (Subscription sub : subs) {
            Notification notification = Notification.builder()
                    .id(UUID.randomUUID())
                    .userId(sub.getProviderId())
                    .message("Perioada de probă expiră în 2 zile. Activează abonamentul pentru a rămâne vizibil.")
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            repository.save(notification);
            websocketService.sendNotificationToUser(sub.getProviderId(), notification);
        }
    }
}
