package com.lfav07.agentscaffold.unit.service;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;
import com.lfav07.agentscaffold.model.stack.GeneralStack;
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

    @Captor
    private ArgumentCaptor<AgentExecutionUnit> executionUnitCaptor;

    private GenerationRequest defaultRequest;

    @BeforeEach
    void setUp() {
        defaultRequest = new GenerationRequest(
                GenerationPreset.ENTERPRISE_SPRING,
                "Test",
                BackendStack.JAVA_SPRING,
                FrontendStack.TYPESCRIPT_REACT,
                null,
                null
        );
        when(appProperties.generation()).thenReturn(
                new AppProperties.Generation("[^a-zA-Z0-9-_]", "-agents.zip")
        );
    }

    @Test
    void generate_shouldReturnGenerationResultWithZipAndFilename() {
        when(presetAgentResolver.resolve(any())).thenReturn(Set.of(CoreAgentType.BACKEND_ARCHITECT));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("MyProject", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("rendered content");
        when(zipGenerator.generate(any())).thenReturn(new byte[]{1, 2, 3});

        GenerationRequest request = new GenerationRequest(
                GenerationPreset.ENTERPRISE_SPRING,
                "MyProject",
                BackendStack.JAVA_SPRING,
                FrontendStack.TYPESCRIPT_REACT,
                null,
                null
        );

        GenerationResult result = generationService.generate(request);

        assertThat(result.zip()).isNotEmpty();
        assertThat(result.filename()).isEqualTo("MyProject-agents.zip");
    }

    @Test
    void generate_shouldSanitizeProjectNameInFilename() {
        when(presetAgentResolver.resolve(any())).thenReturn(Set.of(CoreAgentType.BACKEND_ARCHITECT));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("My Project!", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        GenerationRequest request = new GenerationRequest(
                GenerationPreset.ENTERPRISE_SPRING,
                "My Project!",
                BackendStack.JAVA_SPRING,
                FrontendStack.TYPESCRIPT_REACT,
                null,
                null
        );

        GenerationResult result = generationService.generate(request);

        assertThat(result.filename()).doesNotContain(" ");
        assertThat(result.filename()).doesNotContain("!");
        assertThat(result.filename()).isEqualTo("My-Project--agents.zip");
    }

    @Test
    void generate_shouldIterateOverAllPresetAgents() {
        Set<CoreAgentType> agents = Set.of(
                CoreAgentType.BACKEND_ARCHITECT,
                CoreAgentType.BACKEND_IMPLEMENTER,
                CoreAgentType.BACKEND_TESTER
        );
        when(presetAgentResolver.resolve(any())).thenReturn(agents);
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        generationService.generate(defaultRequest);

        verify(templateEngine, times(3)).buildAgent(any(), any());
    }

    @Test
    void generate_shouldPassFileMapToZipGenerator() {
        when(presetAgentResolver.resolve(any())).thenReturn(Set.of(CoreAgentType.BACKEND_ARCHITECT));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("rendered content");
        when(zipGenerator.generate(any())).thenReturn(new byte[]{1, 2, 3});

        generationService.generate(defaultRequest);

        verify(zipGenerator).generate(fileMapCaptor.capture());
        assertThat(fileMapCaptor.getValue()).containsKey("agents/backend-architect.md");
        assertThat(fileMapCaptor.getValue()).containsValue("rendered content");
    }

    @Test
    void generate_shouldResolveBackendStack_forBackendAgent() {
        when(presetAgentResolver.resolve(any())).thenReturn(Set.of(CoreAgentType.BACKEND_ARCHITECT));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        generationService.generate(defaultRequest);

        verify(contextResolver).resolve(executionUnitCaptor.capture(), any());
        assertThat(executionUnitCaptor.getValue().type()).isEqualTo(CoreAgentType.BACKEND_ARCHITECT);
        assertThat(executionUnitCaptor.getValue().stack()).isEqualTo(BackendStack.JAVA_SPRING);
    }

    @Test
    void generate_shouldResolveFrontendStack_forFrontendAgent() {
        when(presetAgentResolver.resolve(any())).thenReturn(Set.of(CoreAgentType.FRONTEND_ARCHITECT));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        generationService.generate(defaultRequest);

        verify(contextResolver).resolve(executionUnitCaptor.capture(), any());
        assertThat(executionUnitCaptor.getValue().type()).isEqualTo(CoreAgentType.FRONTEND_ARCHITECT);
        assertThat(executionUnitCaptor.getValue().stack()).isEqualTo(FrontendStack.TYPESCRIPT_REACT);
    }

    @Test
    void generate_shouldResolveGeneralStack_forGeneralAgent() {
        when(presetAgentResolver.resolve(any())).thenReturn(Set.of(CoreAgentType.FINAL_REVIEWER));
        when(contextResolver.resolve(any(), any())).thenReturn(
                new AgentRenderContext("Test", "definition")
        );
        when(templateEngine.buildAgent(any(), any())).thenReturn("content");
        when(zipGenerator.generate(any())).thenReturn(new byte[0]);

        generationService.generate(defaultRequest);

        verify(contextResolver).resolve(executionUnitCaptor.capture(), any());
        assertThat(executionUnitCaptor.getValue().type()).isEqualTo(CoreAgentType.FINAL_REVIEWER);
        assertThat(executionUnitCaptor.getValue().stack()).isEqualTo(GeneralStack.GENERAL);
    }
}
