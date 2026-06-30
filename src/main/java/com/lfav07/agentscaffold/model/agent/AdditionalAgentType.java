package com.lfav07.agentscaffold.model.agent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidAgentTypeException;


/*
V2 PLANNED
OPTIONAL AGENTS THAT CAN BE ADDED IN EVERY PRESET BY THE USER
 */
public enum AdditionalAgentType {
    SQL_AGENT;

    /**
     * Resolves a case-insensitive string value to the corresponding additional agent type constant.
     *
     * @param value the string value to resolve (may be null or malformed).
     * @return the matching AdditionalAgentType constant.
     * @throws InvalidAgentTypeException if no match is found or the value is null.
     */
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
