package com.app.linkedin.notification_service.repositories;

import com.app.linkedin.notification_service.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Page<Notification> findAllNotificationByUserId(Long userId,PageRequest pageRequest);
}
