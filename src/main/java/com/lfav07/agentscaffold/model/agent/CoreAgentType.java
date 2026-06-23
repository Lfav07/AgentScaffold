package com.lfav07.agentscaffold.model.agent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidAgentTypeException;


public enum CoreAgentType {
    BACKEND_ARCHITECT,
    FRONTEND_ARCHITECT,
    BACKEND_ARCHITECT_REVIEWER,
    FRONTEND_ARCHITECT_REVIEWER,
    BACKEND_IMPLEMENTER,
    FRONTEND_IMPLEMENTER,
    BACKEND_TESTER,
    FRONTEND_TESTER,
    FINAL_REVIEWER;

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
