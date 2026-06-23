package com.lfav07.agentscaffold.dto;

import com.lfav07.agentscaffold.model.agent.AdditionalAgentType;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.util.Set;

public record GenerationRequest(
        @NotNull
        GenerationPreset preset,

        @NotBlank
        String projectName,

        @NotNull
        BackendStack backendStack,

        @NotNull
        FrontendStack frontendStack,

        Set<CoreAgentType> customCoreAgents,

        Set<AdditionalAgentType> additionalAgents
) {}