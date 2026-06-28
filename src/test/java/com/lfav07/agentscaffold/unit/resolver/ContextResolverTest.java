package com.lfav07.agentscaffold.unit.resolver;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.resolver.ContextResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContextResolverTest {

    private ContextResolver resolver;
    private AgentExecutionUnit unit;

    @BeforeEach
    void setUp() {
        AppProperties appProperties = new AppProperties(
                new AppProperties.Paths("definitions", "templates"),
                new AppProperties.Generation("[^a-zA-Z0-9-_]", "-agents.zip"),
                new AppProperties.Stacks(new AppProperties.Stacks.CategoryLabels("backend", "frontend"))
        );
        resolver = new ContextResolver(appProperties);
        unit = new AgentExecutionUnit(CoreAgentType.BACKEND_ARCHITECT, BackendStack.JAVA_SPRING);
    }

    @Test
    void resolve_shouldReturnAgentRenderContext_whenDefinitionExists() {
        AgentRenderContext result = resolver.resolve(unit, "TestProject");
        assertThat(result).isNotNull();
    }

    @Test
    void resolve_projectNameShouldBePreservedInContext() {
        AgentRenderContext result = resolver.resolve(unit, "PreservedName");
        assertThat(result.projectName()).isEqualTo("PreservedName");
    }

    @Test
    void resolve_shouldThrowTemplateNotFoundException_whenDefinitionMissing() {
        AgentExecutionUnit missing = new AgentExecutionUnit(CoreAgentType.FRONTEND_ARCHITECT, BackendStack.JAVA_SPRING);
        assertThatThrownBy(() -> resolver.resolve(missing, "Test"))
                .isInstanceOf(TemplateNotFoundException.class);
    }
}
