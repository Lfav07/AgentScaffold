package com.lfav07.agentscaffold.resolver;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ContextResolver {
    private final AppProperties appProperties;

    public AgentRenderContext resolve(AgentExecutionUnit unit, String projectName){
       String definitionContent = loadDefinition(unit.resolveDefinitionFileName());
       return new AgentRenderContext(projectName, definitionContent);
    }

    private String loadDefinition(String filePath){
        ClassPathResource finalPath = new ClassPathResource(appProperties.paths().definitions() + "/" + filePath);
        String content;
        try {
            content = finalPath.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TemplateNotFoundException(e.getMessage());
        }
        return content;
    }
}
