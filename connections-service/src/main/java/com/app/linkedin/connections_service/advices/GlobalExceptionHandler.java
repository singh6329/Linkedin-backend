package com.app.linkedin.connections_service.advices;

import com.app.linkedin.connections_service.exceptions.BadRequestException;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException e)
    {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }

}
