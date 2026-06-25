package com.lfav07.agentscaffold.dto;

import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.stack.Stack;
import jakarta.validation.constraints.NotNull;

public record AgentExecutionUnit(
        @NotNull CoreAgentType type,
        @NotNull Stack stack
) {
        public String resolveTemplateFileName() {
                return type.getFileName();
                // "backend-architect"
        }

        public String resolveDefinitionFileName() {
                return stack.getId() + "-" + type.getFileName();
                // "java-spring-backend-architect"
        }

        public String resolveOutputFileName() {
                return "agents/" + type.getFileName() + ".md";
                // "agents/backend-architect.md"
        }
}