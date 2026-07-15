package com.lfav07.agentscaffold.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Describes an available agent type")
public record AgentItem(
        @Schema(description = "Unique identifier for the agent type", example = "BACKEND_ARCHITECT")
        String id,

        @Schema(description = "Human-readable name", example = "Backend Architect")
        String name,

        @Schema(description = "Description of the agent's role", example = "Designs the backend architecture, defines API contracts, and establishes data models.")
        String description
) {
}
