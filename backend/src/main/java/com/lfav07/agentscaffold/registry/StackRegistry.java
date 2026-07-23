package com.lfav07.agentscaffold.registry;

import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StackRegistry {

    private static final Map<BackendStack, StackItem> BACKEND = Map.of(
            BackendStack.JAVA_SPRING,
            new StackItem("java-spring", "Java + Spring Boot"),
            BackendStack.TYPESCRIPT_NODE,
            new StackItem("typescript-node", "TypeScript + Node")

    );

    private static final Map<FrontendStack, StackItem> FRONTEND = Map.of(
            FrontendStack.TYPESCRIPT_REACT,
            new StackItem("typescript-react", "TypeScript + React"),
            FrontendStack.JAVASCRIPT_REACT,
            new StackItem("javascript-react", "JavaScript + React")
    );

    public Set<StackItem> getBackendStacks() {
        return new HashSet<>(BACKEND.values());
    }

    public Set<StackItem> getFrontendStacks() {
        return new HashSet<>(FRONTEND.values());
    }

    public BackendStack fromBackendId(String id) {
        return BackendStack.fromValue(id);
    }

    public FrontendStack fromFrontendId(String id) {
        return FrontendStack.fromValue(id);
    }
}