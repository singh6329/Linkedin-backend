package com.app.linkedin.posts_comment_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;

    private Long postId;

    private Long userId;

    private String comment;

    private LocalDateTime createdAt;
}
