package com.lfav07.agentscaffold.resolver;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContextResolver {
    private final AppProperties appProperties;

    public AgentRenderContext resolve(AgentExecutionUnit unit, String projectName){
        log.debug("Resolving context for agent: {}", unit.type());
        String definitionContent = loadDefinition(unit.resolveDefinitionFileName());
        return new AgentRenderContext(projectName, definitionContent);
    }

    /**
     * Loads a definition file from the classpath under the configured definitions directory.
     *
     * @param filePath the definition file name relative to the definitions directory.
     * @return the definition content as a string.
     * @throws TemplateNotFoundException if the definition file cannot be read.
     */
    private String loadDefinition(String filePath){
        ClassPathResource finalPath = new ClassPathResource(appProperties.paths().definitions() + "/" + filePath);
        log.debug("Definition loaded for agent: {}", filePath);
        String content;
        try {
            content = finalPath.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TemplateNotFoundException(e.getMessage());
        }
        return content;
    }
}
