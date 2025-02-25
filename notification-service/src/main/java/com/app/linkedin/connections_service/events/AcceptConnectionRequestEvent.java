package com.app.linkedin.connections_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcceptConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
