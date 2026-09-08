package com.nehemiah.resolveflowai.exception;

import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(
            NoSuchElementException ex) {
        return error(HttpStatus.NOT_FOUND, "Issue not found");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(
            IllegalArgumentException ex) {
        return error(HttpStatus.BAD_REQUEST, "Invalid request");
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, String>> handleAiError(
            RestClientException ex) {

        log.error("AI service request failed", ex);

        return error(
                HttpStatus.BAD_GATEWAY,
                "Unable to get a response from the AI service");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException ex) {

        log.error("Unexpected server error", ex);

        return error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected server error occurred");
    }

    private ResponseEntity<Map<String, String>> error(
            HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(Map.of("error", message));
    }
}