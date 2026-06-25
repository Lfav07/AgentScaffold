package com.lfav07.agentscaffold.service.impl;

import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.exception.TemplateNotFoundException;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.resolver.ContextResolver;
import com.samskivert.mustache.Mustache;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class TemplateEngineServiceImpl {
    private static final String TEMPLATES_PATH = "templates";

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
        ClassPathResource finalPath = new ClassPathResource(TEMPLATES_PATH + "/" + filePath);
        String content;
        try {
            content = finalPath.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TemplateNotFoundException(e.getMessage());
        }
        return content;
    }

    //test
    static void main() {
        ContextResolver contextResolver = new ContextResolver();
        TemplateEngineServiceImpl templateEngineService = new TemplateEngineServiceImpl();
        AgentExecutionUnit unit = new AgentExecutionUnit(CoreAgentType.BACKEND_ARCHITECT, BackendStack.JAVA_SPRING);
      //  AgentRenderContext context = new AgentRenderContext("pizza!", "loadThis!");
        AgentRenderContext context = contextResolver.resolve(unit, "Pizzeria");
        String s = templateEngineService.buildAgent(unit, context);
        System.out.println(s);
    }
}
