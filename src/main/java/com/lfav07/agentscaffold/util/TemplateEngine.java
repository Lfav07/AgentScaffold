package com.lfav07.agentscaffold.util;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.samskivert.mustache.Mustache;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class TemplateEngine {
    private final AppProperties appProperties;

    public TemplateEngine(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String buildAgent(
            AgentExecutionUnit executionUnit,
            AgentRenderContext renderContext) {

        String templateContent =
                loadTemplate(executionUnit.resolveTemplateFileName());

        return Mustache.compiler()
                .compile(templateContent)
                .execute(Map.of(
                        "projectName", renderContext.projectName(),
                        "stackDefinition", renderContext.stackDefinition()
                ));
    }

    private String loadTemplate(String filePath){
        ClassPathResource finalPath = new ClassPathResource(appProperties.paths().templates() + "/" + filePath);
        String content;
        try {
            content = finalPath.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TemplateNotFoundException(e.getMessage());
        }
        return content;
    }
}
