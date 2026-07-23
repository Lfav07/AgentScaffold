package com.lfav07.agentscaffold.model.stack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lfav07.agentscaffold.exception.InvalidStackException;

public enum FrontendStack implements  Stack {
    TYPESCRIPT_REACT("typescript-react"),
    JAVASCRIPT_REACT("javascript-react");
    @JsonValue
    private final String id;

    FrontendStack(String id) { this.id = id; }

    @Override
    public String getId() { return id; }



    /**
     * Resolves a case-insensitive string value to the corresponding frontend stack constant.
     *
     * @param value the string value to resolve (may be null or malformed).
     * @return the matching FrontendStack constant.
     * @throws InvalidStackException if no match is found or the value is null.
     */
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
