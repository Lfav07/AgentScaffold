package com.lfav07.agentscaffold.registry;

import com.lfav07.agentscaffold.dto.StackItem;
import com.lfav07.agentscaffold.model.stack.BackendStack;
import com.lfav07.agentscaffold.model.stack.FrontendStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StackRegistry {

    private static final Map<BackendStack, StackItem> BACKEND = Map.of(
            BackendStack.JAVA_SPRING,
            new StackItem("java-spring", "Java + Spring Boot")
    );

    private static final Map<FrontendStack, StackItem> FRONTEND = Map.of(
            FrontendStack.TYPESCRIPT_REACT,
            new StackItem("typescript-react", "TypeScript + React")
    );

    public static List<StackItem> getBackendStacks() {
        return new ArrayList<>(BACKEND.values());
    }

    public static List<StackItem> getFrontendStacks() {
        return new ArrayList<>(FRONTEND.values());
    }

    public static BackendStack fromBackendId(String id) {
        return BackendStack.fromValue(id);
    }

    public static FrontendStack fromFrontendId(String id) {
        return FrontendStack.fromValue(id);
    }
}