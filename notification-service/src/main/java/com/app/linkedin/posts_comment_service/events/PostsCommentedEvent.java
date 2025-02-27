package com.app.linkedin.posts_comment_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsCommentedEvent {
    Long postId;
    Long creatorId;
    Long commentedById;
}
