package com.app.linkedin.user_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent {
    private Long userId;
    private String name;
    private String email;
}
