package com.lfav07.agentscaffold.model.stack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lfav07.agentscaffold.exception.InvalidStackException;

public enum GeneralStack implements Stack {
    GENERAL("general");

    private final String id;
    GeneralStack(String id) { this.id = id; }

    @Override
    public String getId() { return id; }

    /**
     * Resolves a case-insensitive string value to the corresponding general stack constant.
     *
     * @param value the string value to resolve (may be null or malformed).
     * @return the matching GeneralStack constant.
     * @throws InvalidStackException if no match is found or the value is null.
     */
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