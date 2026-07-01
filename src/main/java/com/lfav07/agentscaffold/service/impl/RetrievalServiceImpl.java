package com.lfav07.agentscaffold.service.impl;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.registry.AgentRegistry;
import com.lfav07.agentscaffold.registry.PresetRegistry;
import com.lfav07.agentscaffold.registry.StackRegistry;
import com.lfav07.agentscaffold.service.RetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetrievalServiceImpl implements RetrievalService {
    private final AppProperties appProperties;
    private final AgentRegistry agentRegistry;
    private final StackRegistry stackRegistry;
    private final PresetRegistry presetRegistry;

    @Override
    public Set<AgentItem> getCoreAgentsAvailable() {
        Set<AgentItem> agents = agentRegistry.getCoreAgents();
        log.debug("Returning {} core agents", agents.size());
        return agents;
    }

    @Override
    public Map<String, Set<StackItem>> getAllStacksAvailable() {
        Map<String, Set<StackItem>> result = new HashMap<>();
        result.put(appProperties.stacks().categoryLabels().backend(), getBackendStacksAvailable());
        result.put(appProperties.stacks().categoryLabels().frontend(), getFrontendStacksAvailable());
        int total = result.values().stream().mapToInt(Set::size).sum();
        log.debug("Returning {} stacks across all categories", total);
        return result;
    }

    @Override
    public Set<StackItem> getBackendStacksAvailable() {
        Set<StackItem> stacks = stackRegistry.getBackendStacks();
        log.debug("Returning {} backend stacks", stacks.size());
        return stacks;
    }

    @Override
    public Set<StackItem> getFrontendStacksAvailable() {
        Set<StackItem> stacks = stackRegistry.getFrontendStacks();
        log.debug("Returning {} frontend stacks", stacks.size());
        return stacks;
    }

    @Override
    public Set<PresetItem> getPresetsAvailable(){
        Set<PresetItem> presets = presetRegistry.getPresets();
        log.debug("Returning {} presets", presets.size());
        return presets;
    }
}
