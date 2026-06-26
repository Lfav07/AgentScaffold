package com.lfav07.agentscaffold.exception.handler;

import com.lfav07.agentscaffold.exception.ApiErrorResponse;
import com.lfav07.agentscaffold.exception.InvalidAgentTypeException;
import com.lfav07.agentscaffold.exception.InvalidPresetException;
import com.lfav07.agentscaffold.exception.InvalidStackException;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.lfav07.agentscaffold.exception.ZipGenerationException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidPresetException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPreset(InvalidPresetException e, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Invalid generation preset", request);
    }

    @ExceptionHandler(InvalidStackException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidStack(InvalidStackException e, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Invalid stack selection", request);
    }

    @ExceptionHandler(InvalidAgentTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidAgentType(InvalidAgentTypeException e, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Invalid agent type", request);
    }

    @ExceptionHandler(TemplateNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTemplateNotFound(TemplateNotFoundException e, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request);
    }

    @ExceptionHandler(ZipGenerationException.class)
    public ResponseEntity<ApiErrorResponse> handleZipGeneration(ZipGenerationException e, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> fe.getDefaultMessage())
                .orElse("Validation failed");
        return buildError(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleMalformedJson(HttpMessageNotReadableException e, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Malformed request body", request);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NoHandlerFoundException e, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Resource not found", request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return buildError(HttpStatus.METHOD_NOT_ALLOWED, "Method not supported", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(Exception e, HttpServletRequest request) {
        log.error("Unhandled exception", e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }

    private ResponseEntity<ApiErrorResponse> buildError(HttpStatus status, String message, HttpServletRequest request) {
        ApiErrorResponse body = new ApiErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                Instant.now()
        );
        return new ResponseEntity<>(body, status);
    }
}
