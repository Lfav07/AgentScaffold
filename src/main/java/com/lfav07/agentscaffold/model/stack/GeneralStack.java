package com.lfav07.agentscaffold.model.stack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidStackException;

public enum GeneralStack implements Stack {
    GENERAL("general");

    private final String id;
    GeneralStack(String id) { this.id = id; }

    @Override
    public String getId() { return id; }

    @JsonCreator
    public static GeneralStack fromValue(String value) {
        if (value == null) {
            throw new InvalidStackException("General Stack cannot be null");
        }

        String normalized = value.trim()
                .toUpperCase()
                .replace("-", "_");

        try {
            return GeneralStack.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidStackException("Invalid General  Stack: " + value);
        }
    }
}