package com.lfav07.agentscaffold.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AgentRenderContext(
        @NotBlank
        String projectName,

        @NotBlank
        String stackDefinition
) {
}
