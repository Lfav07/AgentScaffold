package com.lfav07.agentscaffold.unit.service;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.fixture.TestEntities;
import com.lfav07.agentscaffold.model.stack.StackCategory;
import com.lfav07.agentscaffold.repository.AgentRepository;
import com.lfav07.agentscaffold.repository.PresetRepository;
import com.lfav07.agentscaffold.repository.StackRepository;
import com.lfav07.agentscaffold.service.impl.RetrievalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrievalServiceImplTest {

    @Mock
    private AppProperties appProperties;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private PresetRepository presetRepository;

    @InjectMocks
    private RetrievalServiceImpl retrievalService;

    @Test
    void getCoreAgentsAvailable_shouldReturnMappedCoreAgents() {
        var agent = TestEntities.backendArchitect();
        when(agentRepository.findByCategory("CORE")).thenReturn(List.of(agent));

        Set<AgentItem> result = retrievalService.getCoreAgentsAvailable();

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().id()).isEqualTo("backend-architect");
    }

    @Test
    void getCoreAgentsAvailable_shouldReturnEmptySet_whenNoAgents() {
        when(agentRepository.findByCategory("CORE")).thenReturn(List.of());

        Set<AgentItem> result = retrievalService.getCoreAgentsAvailable();

        assertThat(result).isEmpty();
    }

    @Test
    void getBackendStacksAvailable_shouldReturnMappedBackendStacks() {
        var stack = TestEntities.javaSpringStack();
        when(stackRepository.findByCategory(StackCategory.BACKEND)).thenReturn(List.of(stack));

        Set<StackItem> result = retrievalService.getBackendStacksAvailable();

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().id()).isEqualTo("java-spring");
    }

    @Test
    void getFrontendStacksAvailable_shouldReturnMappedFrontendStacks() {
        var stack = TestEntities.tsReactStack();
        when(stackRepository.findByCategory(StackCategory.FRONTEND)).thenReturn(List.of(stack));

        Set<StackItem> result = retrievalService.getFrontendStacksAvailable();

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().id()).isEqualTo("typescript-react");
    }

    @Test
    void getAllStacksAvailable_shouldReturnCategorizedMap() {
        var backendStack = TestEntities.javaSpringStack();
        var frontendStack = TestEntities.tsReactStack();
        when(stackRepository.findByCategory(StackCategory.BACKEND)).thenReturn(List.of(backendStack));
        when(stackRepository.findByCategory(StackCategory.FRONTEND)).thenReturn(List.of(frontendStack));
        when(appProperties.stacks()).thenReturn(new AppProperties.Stacks(
                new AppProperties.Stacks.CategoryLabels("backend", "frontend")
        ));

        Map<String, Set<StackItem>> result = retrievalService.getAllStacksAvailable();

        assertThat(result).hasSize(2);
        assertThat(result).containsKeys("backend", "frontend");
        assertThat(result.get("backend")).hasSize(1);
        assertThat(result.get("frontend")).hasSize(1);
    }

    @Test
    void getPresetsAvailable_shouldReturnMappedPresets() {
        var agents = Set.of(TestEntities.backendArchitect());
        var preset = TestEntities.preset("enterprise-fullstack", "Enterprise Fullstack", "Full pipeline", agents);
        when(presetRepository.findAll()).thenReturn(List.of(preset));

        Set<PresetItem> result = retrievalService.getPresetsAvailable();

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().id()).isEqualTo("enterprise-fullstack");
    }
}
