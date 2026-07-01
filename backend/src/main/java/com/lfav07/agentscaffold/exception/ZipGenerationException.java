package com.lfav07.agentscaffold.exception;

public class ZipGenerationException extends RuntimeException {
    public ZipGenerationException(String message) {
        super(message);
    }
    public ZipGenerationException(String message, Exception e) {
        super(message, e);
    }
}
