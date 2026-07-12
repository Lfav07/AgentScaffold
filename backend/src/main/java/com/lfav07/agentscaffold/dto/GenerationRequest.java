package com.lfav07.agentscaffold.dto;

import com.lfav07.agentscaffold.dto.validation.ValidGenerationRequest;
import com.lfav07.agentscaffold.model.agent.AdditionalAgentType;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Schema(description = "Request payload to generate a new project scaffold")
@ValidGenerationRequest
public record GenerationRequest(
        @NotNull
        @Schema(description = "Generation preset to use", example = "Enterprise-fullstack")
        GenerationPreset preset,

        @NotBlank
        @Schema(description = "Name of the project to scaffold", example = "my-app")
        String projectName,

        @Schema(description = "Backend technology stack", example = "JAVA_SPRING",
                nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        BackendStack backendStack,

        @Schema(description = "Frontend technology stack", example = "TYPESCRIPT_REACT",
                nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        FrontendStack frontendStack,

        @Schema(description = "Optional custom core agent types to include")
        Set<CoreAgentType> customCoreAgents,

        @Schema(description = "Optional additional agent types to include")
        Set<AdditionalAgentType> additionalAgents
) {}