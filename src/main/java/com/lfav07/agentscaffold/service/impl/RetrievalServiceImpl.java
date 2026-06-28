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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RetrievalServiceImpl implements RetrievalService {
    private final AppProperties appProperties;
    private final AgentRegistry agentRegistry;
    private final StackRegistry stackRegistry;
    private final PresetRegistry presetRegistry;

    @Override
    public Set<AgentItem> getCoreAgentsAvailable() {
       return agentRegistry.getCoreAgents();
    }

    @Override
    public Map<String, Set<StackItem>> getAllStacksAvailable() {
        Map<String, Set<StackItem>> result = new HashMap<>();
        result.put(appProperties.stacks().categoryLabels().backend(), getBackendStacksAvailable());
        result.put(appProperties.stacks().categoryLabels().frontend(), getFrontendStacksAvailable());
        return result;
    }

    @Override
    public Set<StackItem> getBackendStacksAvailable() {
        return stackRegistry.getBackendStacks();
    }

    @Override
    public Set<StackItem> getFrontendStacksAvailable() {
        return stackRegistry.getFrontendStacks();
    }

    @Override
    public Set<PresetItem> getPresetsAvailable(){
        return presetRegistry.getPresets();
    }
}
