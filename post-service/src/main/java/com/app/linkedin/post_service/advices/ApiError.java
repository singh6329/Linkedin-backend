package com.app.linkedin.post_service.advices;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String error;

    public ApiError() {
        timeStamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String error) {
        this();
        this.status = status;
        this.error = error;
    }
}
