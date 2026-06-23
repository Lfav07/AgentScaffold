package com.lfav07.agentscaffold.model.agent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidAgentTypeException;


/*
V2 PLANNED
OPTIONAL AGENTS THAT CAN BE ADDED IN EVERY PRESET BY THE USER
 */
public enum AdditionalAgentType {
    SQL_AGENT;

    @JsonCreator
    public static AdditionalAgentType fromValue(String value) {
        if (value == null) {
            throw new InvalidAgentTypeException("Additional Agent Type cannot be null");
        }

        String normalized = value.trim()
                .toUpperCase()
                .replace("-", "_");

        try {
            return AdditionalAgentType.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidAgentTypeException("Invalid Additional Agent Type: " + value);
        }
    }
}
