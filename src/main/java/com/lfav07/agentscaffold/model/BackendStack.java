package com.lfav07.agentscaffold.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidStackException;


public enum BackendStack {
    JAVA_SPRING;

    @JsonCreator
    public static BackendStack fromValue(String value) {
        if (value == null) {
            throw new InvalidStackException("Backend Stack cannot be null");
        }

        String normalized = value.trim()
                .toUpperCase()
                .replace("-", "_");

        try {
            return BackendStack.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidStackException("Invalid Backend Stack: " + value);
        }
    }
}
