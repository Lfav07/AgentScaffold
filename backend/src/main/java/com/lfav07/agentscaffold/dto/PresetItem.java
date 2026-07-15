package com.lfav07.agentscaffold.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Describes an available generation preset")
public record PresetItem(
        @Schema(description = "Unique identifier for the preset", example = "FULL_STACK")
        String id,

        @Schema(description = "Human-readable name", example = "Full Stack")
        String name,

        @Schema(description = "Description of what the preset includes", example = "Includes both backend and frontend agents with full CI/CD setup.")
        String description
) {
}
