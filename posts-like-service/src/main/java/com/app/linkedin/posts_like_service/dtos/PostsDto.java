package com.app.linkedin.posts_like_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostsDto {
    private Long id;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;
}
