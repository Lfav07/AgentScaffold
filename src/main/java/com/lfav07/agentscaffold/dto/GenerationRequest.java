package com.lfav07.agentscaffold.dto;

import com.lfav07.agentscaffold.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.util.Set;

public record GenerationRequest(
        @NotNull
        GenerationPreset preset,

        @NotBlank
        String projectName,

        @NotEmpty
        Set<BackendStack> backendStack,

        @NotEmpty
        Set<FrontendStack> frontendStack,

        Set<CoreAgentType> customCoreAgents,

        Set<AdditionalAgentType> additionalAgents
) {}