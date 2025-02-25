package com.app.linkedin.post_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLikedEvent {
    Long postId;
    Long creatorId;
    Long likedByUserId;
}
