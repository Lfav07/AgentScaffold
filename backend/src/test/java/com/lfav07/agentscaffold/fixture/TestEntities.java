package com.lfav07.agentscaffold.fixture;

import com.lfav07.agentscaffold.model.agent.Agent;
import com.lfav07.agentscaffold.model.agent.CoreAgentType;
import com.lfav07.agentscaffold.model.preset.Preset;
import com.lfav07.agentscaffold.model.stack.Stack;
import com.lfav07.agentscaffold.model.stack.StackCategory;
import java.util.Set;

public final class TestEntities {

    public static Stack stack(String key, String name, StackCategory category) {
        return Stack.builder()
                .key(key)
                .name(name)
                .category(category)
                .build();
    }

    public static Stack javaSpringStack() {
        return stack("java-spring", "Java + Spring Boot", StackCategory.BACKEND);
    }

    public static Stack tsReactStack() {
        return stack("typescript-react", "TypeScript + React", StackCategory.FRONTEND);
    }

    public static Stack generalStack() {
        return stack("general", "General", StackCategory.GENERAL);
    }

    public static Agent agent(String key, String name, String description, String slug,
                              CoreAgentType type, Stack stack, String category) {
        return Agent.builder()
                .key(key)
                .name(name)
                .description(description)
                .slug(slug)
                .type(type)
                .stack(stack)
                .category(category)
                .build();
    }

    public static Agent backendArchitect() {
        return agent("backend-architect", "Backend Architect",
                "Designs backend architecture", "backend-architect",
                CoreAgentType.BACKEND_ARCHITECT, javaSpringStack(), "CORE");
    }

    public static Agent frontendArchitect() {
        return agent("frontend-architect", "Frontend Architect",
                "Designs frontend architecture", "frontend-architect",
                CoreAgentType.FRONTEND_ARCHITECT, tsReactStack(), "CORE");
    }

    public static Agent finalReviewer() {
        return agent("final-reviewer", "Final Reviewer",
                "Cross-stack final review", "final-reviewer",
                CoreAgentType.FINAL_REVIEWER, generalStack(), "CORE");
    }

    public static Agent backendImplementer() {
        return agent("backend-implementer", "Backend Implementer",
                "Implements backend code", "backend-implementer",
                CoreAgentType.BACKEND_IMPLEMENTER, javaSpringStack(), "CORE");
    }

    public static Agent frontendImplementer() {
        return agent("frontend-implementer", "Frontend Implementer",
                "Implements frontend code", "frontend-implementer",
                CoreAgentType.FRONTEND_IMPLEMENTER, tsReactStack(), "CORE");
    }

    public static Agent backendTester() {
        return agent("backend-tester", "Backend Tester",
                "Tests backend code", "backend-tester",
                CoreAgentType.BACKEND_TESTER, javaSpringStack(), "CORE");
    }

    public static Agent frontendTester() {
        return agent("frontend-tester", "Frontend Tester",
                "Tests frontend code", "frontend-tester",
                CoreAgentType.FRONTEND_TESTER, tsReactStack(), "CORE");
    }

    public static Agent backendArchitectReviewer() {
        return agent("backend-architect-reviewer", "Backend Architect Reviewer",
                "Reviews backend architecture", "backend-architect-reviewer",
                CoreAgentType.BACKEND_ARCHITECT_REVIEWER, javaSpringStack(), "CORE");
    }

    public static Agent frontendArchitectReviewer() {
        return agent("frontend-architect-reviewer", "Frontend Architect Reviewer",
                "Reviews frontend architecture", "frontend-architect-reviewer",
                CoreAgentType.FRONTEND_ARCHITECT_REVIEWER, tsReactStack(), "CORE");
    }

    public static Preset preset(String key, String name, String description, Set<Agent> agents) {
        return Preset.builder()
                .key(key)
                .name(name)
                .description(description)
                .agents(agents)
                .build();
    }
}
