package com.app.linkedin.post_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent {

    Long creatorId;
    String content;
    Long postId;

}
