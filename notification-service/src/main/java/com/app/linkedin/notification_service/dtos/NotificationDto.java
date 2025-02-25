package com.app.linkedin.notification_service.dtos;

import com.app.linkedin.notification_service.entity.enums.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;

    private String message;

    private NotificationType notificationType;

    private LocalDateTime createdAt;
}
