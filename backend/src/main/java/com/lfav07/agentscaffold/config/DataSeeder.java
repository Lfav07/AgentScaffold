package com.lfav07.agentscaffold.config;

import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.definition.AgentDefinition;
import com.lfav07.agentscaffold.model.preset.Preset;
import com.lfav07.agentscaffold.model.stack.Stack;
import com.lfav07.agentscaffold.model.stack.StackCategory;
import com.lfav07.agentscaffold.repository.AgentDefinitionRepository;
import com.lfav07.agentscaffold.repository.AgentRepository;
import com.lfav07.agentscaffold.repository.PresetRepository;
import com.lfav07.agentscaffold.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final StackRepository stackRepository;
    private final AgentRepository agentRepository;
    private final PresetRepository presetRepository;
    private final AgentDefinitionRepository agentDefinitionRepository;

    @Override
    public void run(String... args) {
        if (stackRepository.count() > 0) return;

        Stack javaSpring = createStack("java-spring", "Java + Spring Boot", StackCategory.BACKEND);
        Stack tsReact = createStack("typescript-react", "TypeScript + React", StackCategory.FRONTEND);
        Stack general = createStack("general", "General", StackCategory.GENERAL);
        stackRepository.saveAll(List.of(javaSpring, tsReact, general));

        Agent backendArchitect = createCoreAgent("backend-architect", "Backend Architect",
                "Designs backend architecture, project structure, and technology decisions",
                "backend-architect", CoreAgentType.BACKEND_ARCHITECT, javaSpring);
        Agent frontendArchitect = createCoreAgent("frontend-architect", "Frontend Architect",
                "Designs frontend architecture, component tree, and UI/UX decisions",
                "frontend-architect", CoreAgentType.FRONTEND_ARCHITECT, tsReact);
        Agent backendArchitectReviewer = createCoreAgent("backend-architect-reviewer", "Backend Architect Reviewer",
                "Reviews backend architecture decisions and ensures best practices",
                "backend-architect-reviewer", CoreAgentType.BACKEND_ARCHITECT_REVIEWER, javaSpring);
        Agent frontendArchitectReviewer = createCoreAgent("frontend-architect-reviewer", "Frontend Architect Reviewer",
                "Reviews frontend architecture decisions and ensures best practices",
                "frontend-architect-reviewer", CoreAgentType.FRONTEND_ARCHITECT_REVIEWER, tsReact);
        Agent backendImplementer = createCoreAgent("backend-implementer", "Backend Implementer",
                "Implements backend code following the architecture blueprint",
                "backend-implementer", CoreAgentType.BACKEND_IMPLEMENTER, javaSpring);
        Agent frontendImplementer = createCoreAgent("frontend-implementer", "Frontend Implementer",
                "Implements frontend components following the architecture blueprint",
                "frontend-implementer", CoreAgentType.FRONTEND_IMPLEMENTER, tsReact);
        Agent backendTester = createCoreAgent("backend-tester", "Backend Tester",
                "Writes and executes backend tests to ensure reliability",
                "backend-tester", CoreAgentType.BACKEND_TESTER, javaSpring);
        Agent frontendTester = createCoreAgent("frontend-tester", "Frontend Tester",
                "Writes and executes frontend tests to ensure reliability",
                "frontend-tester", CoreAgentType.FRONTEND_TESTER, tsReact);
        Agent finalReviewer = createCoreAgent("final-reviewer", "Final Reviewer",
                "Performs cross-stack final review before feature delivery",
                "final-reviewer", CoreAgentType.FINAL_REVIEWER, general);
        agentRepository.saveAll(List.of(backendArchitect, frontendArchitect, backendArchitectReviewer,
                frontendArchitectReviewer, backendImplementer, frontendImplementer,
                backendTester, frontendTester, finalReviewer));

        seedAgentDefinition(backendArchitect);
        seedAgentDefinition(frontendArchitect);
        seedAgentDefinition(backendArchitectReviewer);
        seedAgentDefinition(frontendArchitectReviewer);
        seedAgentDefinition(backendImplementer);
        seedAgentDefinition(frontendImplementer);
        seedAgentDefinition(backendTester);
        seedAgentDefinition(frontendTester);
        seedAgentDefinition(finalReviewer);

        createPreset("enterprise-fullstack", "Enterprise Fullstack",
                "Full pipeline, supporting most complex developments",
                Set.of(backendArchitect, frontendArchitect, backendArchitectReviewer,
                        frontendArchitectReviewer, backendImplementer, frontendImplementer,
                        backendTester, frontendTester, finalReviewer));
        createPreset("enterprise-spring", "Enterprise Spring",
                "Complete backend pipeline for Spring projects with full review and testing",
                Set.of(backendArchitect, backendArchitectReviewer, backendImplementer,
                        backendTester, finalReviewer));
        createPreset("enterprise-react", "Enterprise React",
                "Complete frontend pipeline for React projects with full review and testing",
                Set.of(frontendArchitect, frontendArchitectReviewer, frontendImplementer,
                        frontendTester, finalReviewer));
        createPreset("startup-ready", "Startup Ready",
                "Fast-paced fullstack setup for startups, covering both frontend and backend essentials",
                Set.of(backendArchitect, frontendArchitect, backendImplementer,
                        frontendImplementer, backendTester, frontendTester));
        createPreset("react-ready", "React Ready",
                "Streamlined frontend setup for rapid React development",
                Set.of(frontendArchitect, frontendImplementer, frontendTester));
        createPreset("spring-ready", "Spring Ready",
                "Streamlined backend setup for rapid Spring development",
                Set.of(backendArchitect, backendImplementer, backendTester));
    }

    private Stack createStack(String key, String name, StackCategory category) {
        return Stack.builder().key(key).name(name).category(category).build();
    }

    private Agent createCoreAgent(String key, String name, String description,
                                   String slug, CoreAgentType type, Stack stack) {
        return Agent.builder().key(key).name(name).description(description)
                .slug(slug).type(type).stack(stack).category("CORE").build();
    }

    private void seedAgentDefinition(Agent agent) {
        try {
            String path = "definitions/" + agent.getStack().getKey() + "-" + agent.getSlug() + ".md";
            ClassPathResource resource = new ClassPathResource(path);
            String content = resource.getContentAsString(StandardCharsets.UTF_8);
            agentDefinitionRepository.save(
                    AgentDefinition.builder()
                            .agentKey(agent.getKey())
                            .content(content)
                            .build()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load definition for agent: " + agent.getKey(), e);
        }
    }

    private void createPreset(String key, String name, String description, Set<Agent> agents) {
        Preset preset = Preset.builder().key(key).name(name)
                .description(description).agents(agents).build();
        presetRepository.save(preset);
    }
}
