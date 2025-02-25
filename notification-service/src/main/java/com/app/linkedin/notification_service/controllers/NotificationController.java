package com.app.linkedin.notification_service.controllers;

import com.app.linkedin.notification_service.dtos.NotificationDto;
import com.app.linkedin.notification_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final Integer PAGE_SIZE = 10;

    @GetMapping("/getMyNotifications")
    public ResponseEntity<List<NotificationDto>> getAllMyNotifications(@RequestParam(required = false,defaultValue = "0")Integer pageNumber,
                                                                       @RequestParam(required = false,defaultValue = "createdAt")String sortBy)
    {
        return ResponseEntity.ok(notificationService.getMyAllNotifications(PageRequest.of(pageNumber,PAGE_SIZE, Sort.by(Sort.Direction.DESC,sortBy))));
    }

}
