package com.lfav07.agentscaffold.unit.util;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.util.TemplateEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TemplateEngineTest {

    private TemplateEngine engine;
    private AgentExecutionUnit unit;
    private AgentRenderContext context;

    @BeforeEach
    void setUp() {
        AppProperties appProperties = new AppProperties(
                new AppProperties.Paths("definitions", "templates"),
                new AppProperties.Generation("[^a-zA-Z0-9-_]", "-agents.zip"),
                new AppProperties.Stacks(new AppProperties.Stacks.CategoryLabels("backend", "frontend"))
        );
        engine = new TemplateEngine(appProperties);
        unit = new AgentExecutionUnit(CoreAgentType.BACKEND_ARCHITECT, BackendStack.JAVA_SPRING);
        context = new AgentRenderContext("MyProject", "Java Spring Boot");
    }

    @Test
    void buildAgent_shouldRenderTemplate_whenValidContext() {
        String result = engine.buildAgent(unit, context);
        assertThat(result).isNotEmpty();
    }

    @Test
    void buildAgent_shouldReplacePlaceholders_whenContextHasValues() {
        String result = engine.buildAgent(unit, context);
        assertThat(result).contains("MyProject");
        assertThat(result).contains("Java Spring Boot");
    }

    @Test
    void buildAgent_shouldThrowTemplateNotFoundException_whenTemplateMissing() {
        AppProperties missingProperties = new AppProperties(
                new AppProperties.Paths("definitions", "nonexistent-templates"),
                new AppProperties.Generation("[^a-zA-Z0-9-_]", "-agents.zip"),
                new AppProperties.Stacks(new AppProperties.Stacks.CategoryLabels("backend", "frontend"))
        );
        TemplateEngine missingEngine = new TemplateEngine(missingProperties);
        assertThatThrownBy(() -> missingEngine.buildAgent(unit, context))
                .isInstanceOf(TemplateNotFoundException.class);
    }
}
