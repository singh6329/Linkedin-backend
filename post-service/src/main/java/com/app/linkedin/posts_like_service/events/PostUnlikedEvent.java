package com.app.linkedin.posts_like_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUnlikedEvent {
    Long userId;
    Long postId;
}
