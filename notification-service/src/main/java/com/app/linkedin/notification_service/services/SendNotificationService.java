package com.app.linkedin.notification_service.services;

import com.app.linkedin.notification_service.entity.enums.NotificationType;

public interface SendNotificationService {
    void send(Long userId, String message, NotificationType notificationType);
}
