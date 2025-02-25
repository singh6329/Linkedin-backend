package com.app.linkedin.notification_service.services.Impl;

import com.app.linkedin.notification_service.auth.UserContextHolder;
import com.app.linkedin.notification_service.dtos.NotificationDto;
import com.app.linkedin.notification_service.entity.Notification;
import com.app.linkedin.notification_service.repositories.NotificationRepository;
import com.app.linkedin.notification_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<NotificationDto> getMyAllNotifications(PageRequest pageRequest) {
        Long userId = UserContextHolder.getCurrentUserId();
        List<Notification> notifications = notificationRepository.findAllNotificationByUserId(userId,pageRequest)
                                                .getContent();

        return notifications.stream().map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }
}
