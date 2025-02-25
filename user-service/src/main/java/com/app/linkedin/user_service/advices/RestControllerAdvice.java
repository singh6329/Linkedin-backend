package com.app.linkedin.user_service.advices;

import com.app.linkedin.user_service.exceptions.BadRequestException;
import com.app.linkedin.user_service.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException)
    {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, resourceNotFoundException.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException badRequestException)
    {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, badRequestException.getLocalizedMessage()),HttpStatus.BAD_REQUEST);
    }

}
