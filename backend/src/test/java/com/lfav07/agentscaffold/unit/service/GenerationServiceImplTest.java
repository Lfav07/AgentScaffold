package com.lfav07.agentscaffold.unit.service;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.exception.InvalidStackException;
import com.lfav07.agentscaffold.fixture.TestEntities;
import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.resolver.ContextResolver;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import com.lfav07.agentscaffold.service.impl.GenerationServiceImpl;
import com.lfav07.agentscaffold.util.TemplateEngine;
import com.lfav07.agentscaffold.util.ZipGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerationServiceImplTest {

    @Mock
    private AppProperties appProperties;

    @Mock
    private PresetAgentResolver presetAgentResolver;

    @Mock
    private ContextResolver contextResolver;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private ZipGenerator zipGenerator;

    @InjectMocks
    private GenerationServiceImpl generationService;

    @Captor
    private ArgumentCaptor<Map<String, String>> fileMapCaptor;

    @BeforeEach
    void setUp() {
        lenient().when(appProperties.generation()).thenReturn(
                new AppProperties.Generation("[^a-zA-Z0-9-_]", "-agents.zip")
        );
    }

    @Test
    void generate_shouldReturnZipResult_forValidRequest() {
        Agent agent = TestEntities.backendArchitect();
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(Set.of(agent));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("MyProject", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("rendered content");
        when(zipGenerator.generate(any())).thenReturn(new byte[]{1, 2, 3});

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "MyProject", "java-spring", "typescript-react", null, null
        );

        GenerationResult result = generationService.generate(request);

        assertThat(result.zip()).isNotEmpty();
        assertThat(result.filename()).isEqualTo("MyProject-agents.zip");
    }

    @Test
    void generate_shouldSanitizeProjectNameInFilename() {
        Agent agent = TestEntities.backendArchitect();
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(Set.of(agent));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("My Project!", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "My Project!", "java-spring", "typescript-react", null, null
        );

        GenerationResult result = generationService.generate(request);

        assertThat(result.filename()).doesNotContain(" ");
        assertThat(result.filename()).doesNotContain("!");
        assertThat(result.filename()).isEqualTo("My-Project--agents.zip");
    }

    @Test
    void generate_shouldIterateOverAllPresetAgents() {
        Set<Agent> agents = Set.of(
                TestEntities.backendArchitect(),
                TestEntities.backendImplementer(),
                TestEntities.backendTester()
        );
        when(presetAgentResolver.resolve("enterprise-spring")).thenReturn(agents);
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        GenerationRequest request = new GenerationRequest(
                "enterprise-spring", "Test", "java-spring", null, null, null
        );

        generationService.generate(request);

        verify(templateEngine, times(3)).buildAgent(any(), any());
    }

    @Test
    void generate_shouldResolveBackendStack_forBackendAgent() {
        Agent agent = TestEntities.backendArchitect();
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(Set.of(agent));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "Test", "java-spring", "typescript-react", null, null
        );

        generationService.generate(request);

        ArgumentCaptor<AgentExecutionUnit> unitCaptor = ArgumentCaptor.forClass(AgentExecutionUnit.class);
        verify(contextResolver).resolve(unitCaptor.capture(), any());
        assertThat(unitCaptor.getValue().agent().getSlug()).isEqualTo("backend-architect");
        assertThat(unitCaptor.getValue().stackKey()).isEqualTo("java-spring");
    }

    @Test
    void generate_shouldResolveFrontendStack_forFrontendAgent() {
        Agent agent = TestEntities.frontendArchitect();
        when(presetAgentResolver.resolve("enterprise-react")).thenReturn(Set.of(agent));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        GenerationRequest request = new GenerationRequest(
                "enterprise-react", "Test", null, "typescript-react", null, null
        );

        generationService.generate(request);

        ArgumentCaptor<AgentExecutionUnit> unitCaptor = ArgumentCaptor.forClass(AgentExecutionUnit.class);
        verify(contextResolver).resolve(unitCaptor.capture(), any());
        assertThat(unitCaptor.getValue().agent().getSlug()).isEqualTo("frontend-architect");
        assertThat(unitCaptor.getValue().stackKey()).isEqualTo("typescript-react");
    }

    @Test
    void generate_shouldResolveGeneralStack_forGeneralAgent() {
        Agent agent = TestEntities.finalReviewer();
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(Set.of(agent));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "Test", "java-spring", "typescript-react", null, null
        );

        generationService.generate(request);

        ArgumentCaptor<AgentExecutionUnit> unitCaptor = ArgumentCaptor.forClass(AgentExecutionUnit.class);
        verify(contextResolver).resolve(unitCaptor.capture(), any());
        assertThat(unitCaptor.getValue().agent().getSlug()).isEqualTo("final-reviewer");
        assertThat(unitCaptor.getValue().stackKey()).isEqualTo("general");
    }

    @Test
    void generate_shouldThrow_whenBackendAgentMissingBackendStack() {
        Agent agent = TestEntities.backendArchitect();
        when(presetAgentResolver.resolve("enterprise-react")).thenReturn(Set.of(agent));

        GenerationRequest request = new GenerationRequest(
                "enterprise-react", "Test", null, "typescript-react", null, null
        );

        assertThatThrownBy(() -> generationService.generate(request))
                .isInstanceOf(InvalidStackException.class)
                .hasMessageContaining("Backend stack not provided");
    }

    @Test
    void generate_shouldThrow_whenFrontendAgentMissingFrontendStack() {
        Agent agent = TestEntities.frontendArchitect();
        when(presetAgentResolver.resolve("enterprise-spring")).thenReturn(Set.of(agent));

        GenerationRequest request = new GenerationRequest(
                "enterprise-spring", "Test", "java-spring", null, null, null
        );

        assertThatThrownBy(() -> generationService.generate(request))
                .isInstanceOf(InvalidStackException.class)
                .hasMessageContaining("Frontend stack not provided");
    }

    @Test
    void generate_shouldHandleAdditionalCustomAgents() {
        Set<Agent> agents = Set.of(
                TestEntities.backendArchitect(),
                TestEntities.frontendArchitect()
        );
        when(presetAgentResolver.resolve("enterprise-fullstack")).thenReturn(agents);
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        GenerationRequest request = new GenerationRequest(
                "enterprise-fullstack", "Test", "java-spring", "typescript-react",
                Set.of("custom-agent"), null
        );

        GenerationResult result = generationService.generate(request);

        assertThat(result.zip()).isNotNull();
        verify(zipGenerator).generate(fileMapCaptor.capture());
        assertThat(fileMapCaptor.getValue()).hasSize(2);
    }
}
