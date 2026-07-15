package com.lfav07.agentscaffold.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Standard error response body")
public record ApiErrorResponse(
        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "HTTP status reason phrase", example = "Bad Request")
        String error,

        @Schema(description = "Human-readable error message", example = "Invalid generation preset")
        String message,

        @Schema(description = "Request URI that produced the error", example = "/api/v1/scaffold")
        String path,

        @Schema(description = "Timestamp of when the error occurred", example = "2026-07-01T12:00:00Z")
        Instant timestamp
) {
}
