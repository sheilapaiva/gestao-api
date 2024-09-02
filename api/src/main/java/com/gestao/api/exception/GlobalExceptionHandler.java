package com.gestao.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedException(UnauthorizedException ex) {
        logger.error("UnauthorizedException: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Unauthorized access.");
        error.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExistsException(AlreadyExistsException ex) {
        logger.error("AlreadyExistsException: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Invalid request. Please check your input data.");
        error.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        logger.error("NotFoundException: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Resource not found.");
        error.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidException(InvalidException ex) {
        logger.error("InvalidException: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Invalid request. Please check your input data.");
        error.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("HttpMessageNotReadableException: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Invalid request. Please check your input data.");
        String detailedMessage = ex.getLocalizedMessage();
        if (ex.getCause() != null) {
            detailedMessage = ex.getCause().getLocalizedMessage();
        }
        String firstSentence = detailedMessage.split("\\.")[0];
        error.put("details", firstSentence);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
}
