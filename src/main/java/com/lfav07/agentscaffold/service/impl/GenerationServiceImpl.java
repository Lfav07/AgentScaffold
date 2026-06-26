package com.lfav07.agentscaffold.service.impl;

import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.stack.GeneralStack;
import com.lfav07.agentscaffold.model.stack.Stack;
import com.lfav07.agentscaffold.resolver.ContextResolver;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import com.lfav07.agentscaffold.service.GenerationService;
import com.lfav07.agentscaffold.util.TemplateEngine;
import com.lfav07.agentscaffold.util.ZipGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService {
    private final PresetAgentResolver presetAgentResolver;
    private final ContextResolver contextResolver;
    private final TemplateEngine templateEngine;
    private final ZipGenerator zipGenerator;

    @Override
    public GenerationResult generate(GenerationRequest request) {
        Map<String, String> fileMap = new HashMap<>();
        Set<CoreAgentType> presetAgents = presetAgentResolver.resolve(request.preset());
        for (CoreAgentType type : presetAgents) {
            Stack stack = determineStack(type, request);
            AgentExecutionUnit unit = new AgentExecutionUnit(type, stack);
            AgentRenderContext context = contextResolver.resolve(unit, request.projectName());
            String agentContent = templateEngine.buildAgent(unit, context);
            fileMap.put(unit.resolveOutputFileName(), agentContent);
        }
        byte[] zip = zipGenerator.generate(fileMap);
        String filename = request.projectName()
                .trim()
                .replaceAll("[^a-zA-Z0-9-_]", "-");

        return new GenerationResult(
                zip,
                filename + "-agents.zip"
        );
    }


    private Stack determineStack(CoreAgentType type, GenerationRequest request) {
        return switch (type.getStackCategory()) {
            case BACKEND -> request.backendStack();
            case FRONTEND -> request.frontendStack();
            case GENERAL -> GeneralStack.GENERAL;
        };
    }
}
