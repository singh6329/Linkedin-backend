package com.app.linkedin.post_service.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
public class PostsDto {
    private Long id;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;
}
