package com.app.linkedin.posts_comment_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostsUncommentedEvent {
    private Long postId;
    private Long userId;
}
