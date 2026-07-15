package com.lfav07.agentscaffold.service;

import com.lfav07.agentscaffold.dto.AgentItem;
import com.lfav07.agentscaffold.dto.PresetItem;
import com.lfav07.agentscaffold.dto.StackItem;

import java.util.Map;
import java.util.Set;

public interface RetrievalService {
    Set<AgentItem> getCoreAgentsAvailable();

    Map<String, Set<StackItem>> getAllStacksAvailable();

    Set<StackItem> getBackendStacksAvailable();

    Set<StackItem> getFrontendStacksAvailable();

    Set<PresetItem> getPresetsAvailable();
}
