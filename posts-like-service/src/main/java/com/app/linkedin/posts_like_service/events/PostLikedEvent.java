package com.app.linkedin.posts_like_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLikedEvent {
    Long postId;
    Long creatorId;
    Long likedByUserId;
}
