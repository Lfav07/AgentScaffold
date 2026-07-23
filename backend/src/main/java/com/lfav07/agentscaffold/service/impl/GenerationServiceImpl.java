package com.lfav07.agentscaffold.service.impl;

import com.lfav07.agentscaffold.config.AppProperties;
import com.lfav07.agentscaffold.dto.AgentExecutionUnit;
import com.lfav07.agentscaffold.dto.AgentRenderContext;
import com.lfav07.agentscaffold.dto.GenerationRequest;
import com.lfav07.agentscaffold.dto.GenerationResult;
import com.lfav07.agentscaffold.exception.InvalidStackException;
import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.model.stack.StackCategory;
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

    @Override
    public GenerationResult generate(GenerationRequest request) {
        long start = System.nanoTime();
        log.info("Generation started — preset: {}", request.presetKey());
        Map<String, String> fileMap = new HashMap<>();
        Set<Agent> presetAgents = presetAgentResolver.resolve(request.presetKey());
        log.debug("Preset {} resolved to {} agents", request.presetKey(), presetAgents.size());
        for (Agent agent : presetAgents) {
            String stackKey = determineStackKey(agent, request);
            AgentExecutionUnit unit = new AgentExecutionUnit(agent, stackKey);
            AgentRenderContext context = contextResolver.resolve(unit, request.projectName());
            log.debug("Processing agent: {}", agent.getSlug());
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

    private String determineStackKey(Agent agent, GenerationRequest request) {
        return switch (agent.getStack().getCategory()) {
            case BACKEND -> {
                String bs = request.backendStack();
                if (bs == null) throw new InvalidStackException("Backend stack not provided");
                yield bs;
            }
            case FRONTEND -> {
                String fs = request.frontendStack();
                if (fs == null) throw new InvalidStackException("Frontend stack not provided");
                yield fs;
            }
            case GENERAL -> "general";
        };
    }
}
