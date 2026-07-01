package com.lfav07.agentscaffold.util;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.samskivert.mustache.Mustache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class TemplateEngine {
    private final AppProperties appProperties;

    public TemplateEngine(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * Renders the template by injecting the definition content into the template
     * and returns the resulting content as a string.
     *
     * @param executionUnit contains the agent type and the selected stack.
     * @param renderContext contains the project name and the definition content to inject.
     * @return the rendered template content.
     */
    public String buildAgent(
            AgentExecutionUnit executionUnit,
            AgentRenderContext renderContext) {

        String templateContent =
                loadTemplate(executionUnit.resolveTemplateFileName());

        log.debug("Template rendered for agent: {}", executionUnit.type());

        return Mustache.compiler()
                .compile(templateContent)
                .execute(Map.of(
                        "projectName", renderContext.projectName(),
                        "stackDefinition", renderContext.stackDefinition()
                ));
    }

    /**
     * Loads a template file from the classpath under the configured templates directory.
     *
     * @param filePath the template file name relative to the templates directory.
     * @return the template content as a string.
     * @throws TemplateNotFoundException if the template file cannot be read.
     */
    private String loadTemplate(String filePath){
        ClassPathResource finalPath = new ClassPathResource(appProperties.paths().templates() + "/" + filePath);
        log.debug("Template loaded: {}", filePath);
        String content;
        try {
            content = finalPath.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TemplateNotFoundException(e.getMessage());
        }
        return content;
    }
}
