package com.lfav07.agentscaffold.dto;

import com.lfav07.agentscaffold.dto.validation.ValidGenerationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Schema(description = "Request payload to generate a new project scaffold")
@ValidGenerationRequest
public record GenerationRequest(
        @NotNull
        @Schema(description = "Generation preset key", example = "enterprise-fullstack")
        String presetKey,

        @NotBlank
        @Schema(description = "Name of the project to scaffold", example = "my-app")
        String projectName,

        @Schema(description = "Backend technology stack key", example = "java-spring",
                nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String backendStack,

        @Schema(description = "Frontend technology stack key", example = "typescript-react",
                nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String frontendStack,

        @Schema(description = "Optional custom core agent keys to include")
        Set<String> customCoreAgents,

        @Schema(description = "Optional additional agent keys to include")
        Set<String> additionalAgents
) {}
