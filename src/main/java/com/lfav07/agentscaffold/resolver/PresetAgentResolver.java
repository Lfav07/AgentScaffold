package com.lfav07.agentscaffold.resolver;

import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.GenerationPreset;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class PresetAgentResolver {

    private static final Map<GenerationPreset, Set<CoreAgentType>> PRESET_AGENTS =
            Map.of(
                    GenerationPreset.ENTERPRISE_FULLSTACK, Set.of(
                            CoreAgentType.BACKEND_ARCHITECT,
                            CoreAgentType.FRONTEND_ARCHITECT,
                            CoreAgentType.BACKEND_ARCHITECT_REVIEWER,
                            CoreAgentType.FRONTEND_ARCHITECT_REVIEWER,
                            CoreAgentType.BACKEND_IMPLEMENTER,
                            CoreAgentType.FRONTEND_IMPLEMENTER,
                            CoreAgentType.BACKEND_TESTER,
                            CoreAgentType.FRONTEND_TESTER,
                            CoreAgentType.FINAL_REVIEWER
                    ),
                    GenerationPreset.ENTERPRISE_SPRING, Set.of(
                            CoreAgentType.BACKEND_ARCHITECT,
                            CoreAgentType.BACKEND_ARCHITECT_REVIEWER,
                            CoreAgentType.BACKEND_IMPLEMENTER,
                            CoreAgentType.BACKEND_TESTER,
                            CoreAgentType.FINAL_REVIEWER
                    ),
                    GenerationPreset.ENTERPRISE_REACT, Set.of(
                            CoreAgentType.FRONTEND_ARCHITECT,
                            CoreAgentType.FRONTEND_ARCHITECT_REVIEWER,
                            CoreAgentType.FRONTEND_IMPLEMENTER,
                            CoreAgentType.FRONTEND_TESTER,
                            CoreAgentType.FINAL_REVIEWER
                    ),
                    GenerationPreset.STARTUP_READY, Set.of(
                            CoreAgentType.BACKEND_ARCHITECT,
                            CoreAgentType.FRONTEND_ARCHITECT,
                            CoreAgentType.BACKEND_IMPLEMENTER,
                            CoreAgentType.FRONTEND_IMPLEMENTER,
                            CoreAgentType.BACKEND_TESTER,
                            CoreAgentType.FRONTEND_TESTER
                    ),
                    GenerationPreset.REACT_READY, Set.of(
                            CoreAgentType.FRONTEND_ARCHITECT,
                            CoreAgentType.FRONTEND_IMPLEMENTER,
                            CoreAgentType.FRONTEND_TESTER
                    ),
                    GenerationPreset.SPRING_READY, Set.of(
                            CoreAgentType.BACKEND_ARCHITECT,
                            CoreAgentType.BACKEND_IMPLEMENTER,
                            CoreAgentType.BACKEND_TESTER
                    )


            );

    public Set<CoreAgentType> resolve(GenerationPreset preset) {
        return PRESET_AGENTS.getOrDefault(preset, Set.of());
    }
}