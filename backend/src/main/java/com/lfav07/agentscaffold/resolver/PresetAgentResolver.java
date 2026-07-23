package com.lfav07.agentscaffold.resolver;

import com.lfav07.agentscaffold.exception.InvalidPresetException;
import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.repository.PresetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class PresetAgentResolver {
    private final PresetRepository presetRepository;

    public Set<Agent> resolve(String presetKey) {
        com.lfav07.agentscaffold.model.preset.Preset preset = presetRepository
                .findByKeyWithAgents(presetKey)
                .orElseThrow(() -> new InvalidPresetException("Preset not found: " + presetKey));
        log.debug("Preset {} resolved to {} agents", presetKey, preset.getAgents().size());
        return preset.getAgents();
    }
}
