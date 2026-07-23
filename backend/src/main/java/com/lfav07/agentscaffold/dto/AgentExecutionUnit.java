package com.lfav07.agentscaffold.dto;

import com.lfav07.agentscaffold.model.agent.Agent;
import jakarta.validation.constraints.NotNull;

public record AgentExecutionUnit(
        @NotNull Agent agent,
        @NotNull String stackKey
) {
        public String resolveTemplateFileName() {
                return agent.getSlug() + ".md";
        }

        public String resolveDefinitionFileName() {
                return stackKey + "-" + agent.getSlug() + ".md";
        }

        public String resolveOutputFileName() {
                return "agents/" + agent.getSlug() + ".md";
        }
}
