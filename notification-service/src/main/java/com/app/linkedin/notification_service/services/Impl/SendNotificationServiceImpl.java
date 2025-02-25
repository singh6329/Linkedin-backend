package com.app.linkedin.notification_service.services.Impl;

import com.app.linkedin.notification_service.entity.Notification;
import com.app.linkedin.notification_service.entity.enums.NotificationType;
import com.app.linkedin.notification_service.repositories.NotificationRepository;
import com.app.linkedin.notification_service.services.SendNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendNotificationServiceImpl implements SendNotificationService {

    private final NotificationRepository notificationRepository;

    public void send(Long userId, String message, NotificationType notificationType)
    {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);
        notification.setNotificationType(notificationType);
        notificationRepository.save(notification);
    }
}
