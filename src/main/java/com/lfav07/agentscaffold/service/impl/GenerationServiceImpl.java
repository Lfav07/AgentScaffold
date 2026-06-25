package com.lfav07.agentscaffold.service.impl;

import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import com.lfav07.agentscaffold.service.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public final class GenerationServiceImpl implements GenerationService {
    public PresetAgentResolver presetAgentResolver = new PresetAgentResolver();

    public GenerationResult generate(GenerationRequest request) {
        Set<CoreAgentType> presetAgents = presetAgentResolver.resolve(request.preset());

        //temp/todo
        return new GenerationResult(null);
    }
}
