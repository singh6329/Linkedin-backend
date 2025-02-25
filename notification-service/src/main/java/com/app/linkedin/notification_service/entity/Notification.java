package com.app.linkedin.notification_service.entity;

import com.app.linkedin.notification_service.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
