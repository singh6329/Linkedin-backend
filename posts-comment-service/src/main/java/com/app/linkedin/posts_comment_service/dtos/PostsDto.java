package com.app.linkedin.posts_comment_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostsDto {
    private Long id;

    private Long userId;

    private String content;

    private Long likesCount;

    private Long commentsCount;

    private LocalDateTime createdAt;
}
