package com.lfav07.agentscaffold.resolver;

import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.lfav07.agentscaffold.repository.AgentDefinitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContextResolver {
    private final AgentDefinitionRepository agentDefinitionRepository;

    public AgentRenderContext resolve(AgentExecutionUnit unit, String projectName){
        log.debug("Resolving context for agent: {}", unit.agent().getSlug());
        String definitionContent = loadDefinition(unit.agent().getKey());
        return new AgentRenderContext(projectName, definitionContent);
    }

    private String loadDefinition(String agentKey){
        return agentDefinitionRepository.findByAgentKey(agentKey)
                .map(d -> d.getContent())
                .orElseThrow(() -> new TemplateNotFoundException(
                        "Definition not found for agent: " + agentKey));
    }
}
