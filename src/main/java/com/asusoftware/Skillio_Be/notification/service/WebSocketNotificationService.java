package com.asusoftware.Skillio_Be.notification.service;

import com.asusoftware.Skillio_Be.notification.model.Notification;
import com.asusoftware.Skillio_Be.notification.model.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(UUID userId, Notification notification) {
        NotificationDto dto = NotificationDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();

        messagingTemplate.convertAndSend("/topic/notifications/" + userId, dto);
    }
}

