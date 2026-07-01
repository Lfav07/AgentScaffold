package com.lfav07.agentscaffold.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Describes an available technology stack")
public record StackItem(
        @Schema(description = "Unique identifier for the stack", example = "SPRING_BOOT")
        String id,

        @Schema(description = "Human-readable name", example = "Spring Boot")
        String name
) {}