package com.lfav07.agentscaffold.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidStackException;

public enum FrontendStack {
    TYPESCRIPT_REACT;

    @JsonCreator
    public static FrontendStack fromValue(String value) {
        if (value == null) {
            throw new InvalidStackException("Frontend Stack cannot be null");
        }

        String normalized = value.trim()
                .toUpperCase()
                .replace("-", "_");

        try {
            return FrontendStack.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidStackException("Invalid Frontend Stack: " + value);
        }
    }

}
