package com.lfav07.agentscaffold.unit.resolver;

import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

class PresetAgentResolverTest {

    private final PresetAgentResolver resolver = new PresetAgentResolver();

    @Test
    void resolve_shouldReturnEnterpriseFullstackAgents_forEnterpriseFullstackPreset() {
        Set<CoreAgentType> agents = resolver.resolve(GenerationPreset.ENTERPRISE_FULLSTACK);
        assertThat(agents).containsExactlyInAnyOrder(
                CoreAgentType.BACKEND_ARCHITECT,
                CoreAgentType.FRONTEND_ARCHITECT,
                CoreAgentType.BACKEND_ARCHITECT_REVIEWER,
                CoreAgentType.FRONTEND_ARCHITECT_REVIEWER,
                CoreAgentType.BACKEND_IMPLEMENTER,
                CoreAgentType.FRONTEND_IMPLEMENTER,
                CoreAgentType.BACKEND_TESTER,
                CoreAgentType.FRONTEND_TESTER,
                CoreAgentType.FINAL_REVIEWER
        );
    }

    @Test
    void resolve_shouldReturnEnterpriseSpringAgents_forEnterpriseSpringPreset() {
        Set<CoreAgentType> agents = resolver.resolve(GenerationPreset.ENTERPRISE_BACKEND);
        assertThat(agents).containsExactlyInAnyOrder(
                CoreAgentType.BACKEND_ARCHITECT,
                CoreAgentType.BACKEND_ARCHITECT_REVIEWER,
                CoreAgentType.BACKEND_IMPLEMENTER,
                CoreAgentType.BACKEND_TESTER,
                CoreAgentType.FINAL_REVIEWER
        );
    }

}
