package com.lfav07.agentscaffold.service.impl;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.model.stack.StackCategory;
import com.lfav07.agentscaffold.repository.AgentRepository;
import com.lfav07.agentscaffold.repository.PresetRepository;
import com.lfav07.agentscaffold.repository.StackRepository;
import com.lfav07.agentscaffold.service.RetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetrievalServiceImpl implements RetrievalService {
    private final AppProperties appProperties;
    private final AgentRepository agentRepository;
    private final StackRepository stackRepository;
    private final PresetRepository presetRepository;

    @Override
    public Set<AgentItem> getCoreAgentsAvailable() {
        Set<AgentItem> agents = agentRepository.findByCategory("CORE").stream()
                .map(a -> new AgentItem(a.getKey(), a.getName(), a.getDescription()))
                .collect(Collectors.toSet());
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
        Set<StackItem> stacks = stackRepository.findByCategory(StackCategory.BACKEND).stream()
                .map(s -> new StackItem(s.getKey(), s.getName()))
                .collect(Collectors.toSet());
        log.debug("Returning {} backend stacks", stacks.size());
        return stacks;
    }

    @Override
    public Set<StackItem> getFrontendStacksAvailable() {
        Set<StackItem> stacks = stackRepository.findByCategory(StackCategory.FRONTEND).stream()
                .map(s -> new StackItem(s.getKey(), s.getName()))
                .collect(Collectors.toSet());
        log.debug("Returning {} frontend stacks", stacks.size());
        return stacks;
    }

    @Override
    public Set<PresetItem> getPresetsAvailable(){
        Set<PresetItem> presets = presetRepository.findAll().stream()
                .map(p -> new PresetItem(p.getKey(), p.getName(), p.getDescription()))
                .collect(Collectors.toSet());
        log.debug("Returning {} presets", presets.size());
        return presets;
    }
}
