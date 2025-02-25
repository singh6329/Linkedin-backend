package com.app.linkedin.connections_service.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    LocalDateTime timeStamp;
    HttpStatus httpStatus;
    String error;

    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus,String error)
    {
        this();
        this.error=error;
        this.httpStatus=httpStatus;
    }
}
