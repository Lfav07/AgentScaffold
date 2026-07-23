package com.lfav07.agentscaffold.unit.resolver;

import com.lfav07.agentscaffold.exception.InvalidPresetException;
import com.lfav07.agentscaffold.fixture.TestEntities;
import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.model.preset.Preset;
import com.lfav07.agentscaffold.repository.PresetRepository;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PresetAgentResolverTest {

    @Mock
    private PresetRepository presetRepository;

    @InjectMocks
    private PresetAgentResolver resolver;

    @Test
    void resolve_shouldReturnAgents_forValidPresetKey() {
        Set<Agent> agents = Set.of(TestEntities.backendArchitect());
        Preset preset = TestEntities.preset("enterprise-spring", "Enterprise Spring", "Desc", agents);
        when(presetRepository.findByKeyWithAgents("enterprise-spring")).thenReturn(Optional.of(preset));

        Set<Agent> result = resolver.resolve("enterprise-spring");

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getKey()).isEqualTo("backend-architect");
    }

    @Test
    void resolve_shouldThrowInvalidPresetException_forUnknownKey() {
        when(presetRepository.findByKeyWithAgents("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resolver.resolve("unknown"))
                .isInstanceOf(InvalidPresetException.class)
                .hasMessageContaining("Preset not found");
    }

    @Test
    void resolve_shouldReturnAllPresetAgents() {
        Set<Agent> agents = Set.of(
                TestEntities.backendArchitect(),
                TestEntities.frontendArchitect(),
                TestEntities.finalReviewer()
        );
        Preset preset = TestEntities.preset("enterprise-fullstack", "Enterprise Fullstack", "Desc", agents);
        when(presetRepository.findByKeyWithAgents("enterprise-fullstack")).thenReturn(Optional.of(preset));

        Set<Agent> result = resolver.resolve("enterprise-fullstack");

        assertThat(result).hasSize(3);
    }
}
