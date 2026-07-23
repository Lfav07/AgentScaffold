package com.lfav07.agentscaffold.unit.dto;

import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.fixture.TestEntities;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AgentExecutionUnitTest {

    private final AgentExecutionUnit unit = new AgentExecutionUnit(
            TestEntities.backendArchitect(), "java-spring"
    );

    @Test
    void resolveTemplateFileName_shouldAppendMdSuffix() {
        assertThat(unit.resolveTemplateFileName())
                .isEqualTo("backend-architect.md");
    }

    @Test
    void resolveDefinitionFileName_shouldCombineStackKeyAndSlug() {
        assertThat(unit.resolveDefinitionFileName())
                .isEqualTo("java-spring-backend-architect.md");
    }

    @Test
    void resolveOutputFileName_shouldPrependAgentsPrefix() {
        assertThat(unit.resolveOutputFileName())
                .startsWith("agents/");
    }

    @Test
    void resolveOutputFileName_shouldReturnAgentSubdirectoryPath() {
        assertThat(unit.resolveOutputFileName())
                .isEqualTo("agents/backend-architect.md");
    }

    @Test
    void resolveDefinitionFileName_withDifferentStackKey() {
        AgentExecutionUnit frontendUnit = new AgentExecutionUnit(
                TestEntities.frontendArchitect(), "typescript-react"
        );
        assertThat(frontendUnit.resolveDefinitionFileName())
                .isEqualTo("typescript-react-frontend-architect.md");
    }
}
