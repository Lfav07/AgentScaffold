package com.lfav07.agentscaffold.model.agent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidAgentTypeException;
import com.lfav07.agentscaffold.model.stack.StackCategory;


public enum CoreAgentType {

    BACKEND_ARCHITECT(StackCategory.BACKEND, "backend-architect"),
    BACKEND_ARCHITECT_REVIEWER(StackCategory.BACKEND, "backend-architect-reviewer"),
    BACKEND_IMPLEMENTER(StackCategory.BACKEND, "backend-implementer"),
    BACKEND_TESTER(StackCategory.BACKEND, "backend-tester"),
    FRONTEND_ARCHITECT(StackCategory.FRONTEND, "frontend-architect"),
    FRONTEND_ARCHITECT_REVIEWER(StackCategory.FRONTEND, "frontend-architect-reviewer"),
    FRONTEND_IMPLEMENTER(StackCategory.FRONTEND, "frontend-implementer"),
    FRONTEND_TESTER(StackCategory.FRONTEND, "frontend-tester"),
    FINAL_REVIEWER(StackCategory.GENERAL, "general-final-reviewer");

    private final StackCategory stackCategory;
    private final String fileName;

    CoreAgentType(StackCategory stackCategory, String fileName) {
        this.stackCategory = stackCategory;
        this.fileName = fileName;
    }

    public String getFileName() { return fileName; }
    public StackCategory getStackCategory() { return stackCategory; }


    @JsonCreator
    public static CoreAgentType fromValue(String value) {
        if (value == null) {
            throw new InvalidAgentTypeException("Core Agent Type cannot be null");
        }

        String normalized = value.trim()
                .toUpperCase()
                .replace("-", "_");

        try {
            return CoreAgentType.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidAgentTypeException("Invalid Core Agent Type: " + value);
        }
    }

}
