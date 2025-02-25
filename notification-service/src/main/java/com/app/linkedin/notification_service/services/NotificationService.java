package com.app.linkedin.notification_service.services;

import com.app.linkedin.notification_service.dtos.NotificationDto;
import com.app.linkedin.notification_service.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getMyAllNotifications(PageRequest pageRequest);
}
