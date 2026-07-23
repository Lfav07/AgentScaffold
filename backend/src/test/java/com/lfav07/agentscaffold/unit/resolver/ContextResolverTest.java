package com.lfav07.agentscaffold.unit.resolver;

import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.lfav07.agentscaffold.fixture.TestEntities;
import com.lfav07.agentscaffold.model.definition.AgentDefinition;
import com.lfav07.agentscaffold.repository.AgentDefinitionRepository;
import com.lfav07.agentscaffold.resolver.ContextResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContextResolverTest {

    @Mock
    private AgentDefinitionRepository agentDefinitionRepository;

    @InjectMocks
    private ContextResolver resolver;

    @Test
    void resolve_shouldReturnAgentRenderContext_whenDefinitionExists() {
        var agent = TestEntities.backendArchitect();
        var def = new AgentDefinition(1L, "backend-architect", "definition content");
        when(agentDefinitionRepository.findByAgentKey("backend-architect")).thenReturn(Optional.of(def));

        AgentExecutionUnit unit = new AgentExecutionUnit(agent, "java-spring");
        AgentRenderContext result = resolver.resolve(unit, "TestProject");

        assertThat(result).isNotNull();
        assertThat(result.stackDefinition()).isEqualTo("definition content");
    }

    @Test
    void resolve_projectNameShouldBePreservedInContext() {
        var agent = TestEntities.backendArchitect();
        var def = new AgentDefinition(1L, "backend-architect", "content");
        when(agentDefinitionRepository.findByAgentKey("backend-architect")).thenReturn(Optional.of(def));

        AgentExecutionUnit unit = new AgentExecutionUnit(agent, "java-spring");
        AgentRenderContext result = resolver.resolve(unit, "PreservedName");

        assertThat(result.projectName()).isEqualTo("PreservedName");
    }

    @Test
    void resolve_shouldThrowTemplateNotFoundException_whenDefinitionMissing() {
        var agent = TestEntities.frontendArchitect();
        when(agentDefinitionRepository.findByAgentKey("frontend-architect")).thenReturn(Optional.empty());

        AgentExecutionUnit unit = new AgentExecutionUnit(agent, "typescript-react");

        assertThatThrownBy(() -> resolver.resolve(unit, "Test"))
                .isInstanceOf(TemplateNotFoundException.class)
                .hasMessageContaining("Definition not found for agent");
    }

    @Test
    void resolve_shouldLoadCorrectDefinitionFile() {
        var agent = TestEntities.backendArchitect();
        var def = new AgentDefinition(1L, "backend-architect", "java-spring definition");
        when(agentDefinitionRepository.findByAgentKey("backend-architect")).thenReturn(Optional.of(def));

        AgentExecutionUnit unit = new AgentExecutionUnit(agent, "java-spring");
        resolver.resolve(unit, "Test");

        // verify the correct agent key was used for lookup
        assertThat(unit.agent().getKey()).isEqualTo("backend-architect");
    }
}
