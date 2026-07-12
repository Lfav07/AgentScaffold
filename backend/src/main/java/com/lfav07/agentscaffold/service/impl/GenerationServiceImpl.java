package com.lfav07.agentscaffold.service.impl;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.exception.InvalidStackException;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;
import com.lfav07.agentscaffold.model.stack.GeneralStack;
import com.lfav07.agentscaffold.model.stack.Stack;
import com.lfav07.agentscaffold.resolver.ContextResolver;
import com.lfav07.agentscaffold.resolver.PresetAgentResolver;
import com.lfav07.agentscaffold.service.GenerationService;
import com.lfav07.agentscaffold.util.TemplateEngine;
import com.lfav07.agentscaffold.util.ZipGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService {
    private final AppProperties appProperties;
    private final PresetAgentResolver presetAgentResolver;
    private final ContextResolver contextResolver;
    private final TemplateEngine templateEngine;
    private final ZipGenerator zipGenerator;

    /**
     * Generates a project ZIP by resolving preset agents, rendering templates for each agent,
     * and packaging the resulting files into a byte array.
     *
     * @param request the generation request containing project name, stacks, and agents.
     * @return the generation result containing the ZIP bytes and filename.
     */
    @Override
    public GenerationResult generate(GenerationRequest request) {
        long start = System.nanoTime();
        log.info("Generation started — preset: {}", request.preset());
        Map<String, String> fileMap = new HashMap<>();
        Set<CoreAgentType> presetAgents = presetAgentResolver.resolve(request.preset());
        log.debug("Preset {} resolved to {} agents", request.preset(), presetAgents.size());
        for (CoreAgentType type : presetAgents) {
            Stack stack = determineStack(type, request);
            AgentExecutionUnit unit = new AgentExecutionUnit(type, stack);
            AgentRenderContext context = contextResolver.resolve(unit, request.projectName());
            log.debug("Processing agent: {}", type);
            String agentContent = templateEngine.buildAgent(unit, context);
            fileMap.put(unit.resolveOutputFileName(), agentContent);
        }
        byte[] zip = zipGenerator.generate(fileMap);
        String filename = request.projectName()
                .trim()
                .replaceAll(appProperties.generation().sanitizeRegex(), "-");


        long durationMs = Duration.ofNanos(System.nanoTime() - start).toMillis();

        log.info("Generation completed — project: {}, total size: {} bytes, duration: {} ms", request.projectName(), zip.length, durationMs);
        return new GenerationResult(
                zip,
                filename + appProperties.generation().zipSuffix()
        );
    }


    /**
     * Resolves the appropriate stack enum for a given core agent type based on its stack category
     * and the stacks provided in the generation request.
     *
     * @param type    the core agent type whose stack category drives selection.
     * @param request the generation request carrying backend/frontend stack choices.
     * @return the resolved stack (BackendStack, FrontendStack, or GeneralStack.GENERAL).
     */
    private Stack determineStack(CoreAgentType type, GenerationRequest request) {
        return switch (type.getStackCategory()) {
            case BACKEND -> {
                BackendStack bs = request.backendStack();
                if (bs == null) throw new InvalidStackException("Backend stack not provided");
                yield bs;
            }
            case FRONTEND -> {
                FrontendStack fs = request.frontendStack();
                if (fs == null) throw new InvalidStackException("Frontend stack not provided");
                yield fs;
            }
            case GENERAL -> GeneralStack.GENERAL;
        };
    }
}
