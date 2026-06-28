package com.lfav07.agentscaffold.unit.service;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.registry.AgentRegistry;
import com.lfav07.agentscaffold.registry.PresetRegistry;
import com.lfav07.agentscaffold.registry.StackRegistry;
import com.lfav07.agentscaffold.service.impl.RetrievalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrievalServiceImplTest {

    @Mock
    private AppProperties appProperties;

    @Mock
    private AgentRegistry agentRegistry;

    @Mock
    private StackRegistry stackRegistry;

    @Mock
    private PresetRegistry presetRegistry;

    @InjectMocks
    private RetrievalServiceImpl retrievalService;

    @Test
    void getCoreAgentsAvailable_shouldDelegateToAgentRegistry() {
        Set<AgentItem> expected = Set.of(
                new AgentItem("backend-architect", "Backend Architect", "Lorem ipsum")
        );
        when(agentRegistry.getCoreAgents()).thenReturn(expected);

        Set<AgentItem> result = retrievalService.getCoreAgentsAvailable();

        assertThat(result).isEqualTo(expected);
        verify(agentRegistry).getCoreAgents();
    }

    @Test
    void getAllStacksAvailable_shouldMapCategoriesWithLabels() {
        Set<StackItem> backendStacks = Set.of(new StackItem("java-spring", "Java + Spring Boot"));
        Set<StackItem> frontendStacks = Set.of(new StackItem("typescript-react", "TypeScript + React"));

        when(appProperties.stacks()).thenReturn(new AppProperties.Stacks(
                new AppProperties.Stacks.CategoryLabels("backend", "frontend")
        ));
        when(stackRegistry.getBackendStacks()).thenReturn(backendStacks);
        when(stackRegistry.getFrontendStacks()).thenReturn(frontendStacks);

        Map<String, Set<StackItem>> result = retrievalService.getAllStacksAvailable();

        assertThat(result).hasSize(2);
        assertThat(result).containsKey("backend");
        assertThat(result).containsKey("frontend");
        assertThat(result.get("backend")).isEqualTo(backendStacks);
        assertThat(result.get("frontend")).isEqualTo(frontendStacks);
        verify(stackRegistry).getBackendStacks();
        verify(stackRegistry).getFrontendStacks();
    }

    @Test
    void getBackendStacksAvailable_shouldDelegateToStackRegistry() {
        Set<StackItem> expected = Set.of(new StackItem("java-spring", "Java + Spring Boot"));
        when(stackRegistry.getBackendStacks()).thenReturn(expected);

        Set<StackItem> result = retrievalService.getBackendStacksAvailable();

        assertThat(result).isEqualTo(expected);
        verify(stackRegistry).getBackendStacks();
    }

    @Test
    void getFrontendStacksAvailable_shouldDelegateToStackRegistry() {
        Set<StackItem> expected = Set.of(new StackItem("typescript-react", "TypeScript + React"));
        when(stackRegistry.getFrontendStacks()).thenReturn(expected);

        Set<StackItem> result = retrievalService.getFrontendStacksAvailable();

        assertThat(result).isEqualTo(expected);
        verify(stackRegistry).getFrontendStacks();
    }

    @Test
    void getPresetsAvailable_shouldDelegateToPresetRegistry() {
        Set<PresetItem> expected = Set.of(
                new PresetItem("enterprise-fullstack", "Enterprise Fullstack", "Lorem ipsum")
        );
        when(presetRegistry.getPresets()).thenReturn(expected);

        Set<PresetItem> result = retrievalService.getPresetsAvailable();

        assertThat(result).isEqualTo(expected);
        verify(presetRegistry).getPresets();
    }
}
